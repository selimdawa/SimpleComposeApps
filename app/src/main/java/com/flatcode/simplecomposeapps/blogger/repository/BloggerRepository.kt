package com.flatcode.simplecomposeapps.blogger.repository

import com.flatcode.simplecomposeapps.blogger.db.BloggerCommentEntity
import com.flatcode.simplecomposeapps.blogger.db.BloggerDao
import com.flatcode.simplecomposeapps.blogger.db.BloggerPageEntity
import com.flatcode.simplecomposeapps.blogger.db.BloggerPostEntity
import com.flatcode.simplecomposeapps.blogger.network.BloggerApi
import com.flatcode.simplecomposeapps.blogger.model.Author
import com.flatcode.simplecomposeapps.blogger.model.BloggerResponse
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.blogger.model.CommentItem
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BloggerRepository @Inject constructor(
    private val api: BloggerApi,
    private val dao: BloggerDao
) {
    fun getCachedPosts(): Flow<List<Post>> = dao.getAllPosts().map { entities ->
        entities.map { mapFromEntity(it) }
    }

    fun getCachedPost(postId: String): Flow<Post?> = dao.getPostById(postId).map { entity ->
        entity?.let { mapFromEntity(it) }
    }

    fun getCachedPages(): Flow<List<Page>> = dao.getAllPages().map { entities ->
        entities.map { entity ->
            mapPageFromEntity(entity)
        }
    }

    fun getCachedPage(pageId: String): Flow<Page?> = dao.getPageById(pageId).map { entity ->
        entity?.let { mapPageFromEntity(it) }
    }

    fun getCachedComments(postId: String): Flow<List<Comment>> = dao.getCommentsForPost(postId).map { entities ->
        entities.map { entity ->
            Comment(
                id = entity.id,
                name = entity.name,
                profileImage = entity.profileImage,
                published = entity.published,
                comment = entity.comment
            )
        }
    }

    suspend fun insertPosts(posts: List<Post>) {
        val entities = posts.map { post ->
            BloggerPostEntity(
                id = post.id ?: "",
                author = post.author,
                content = post.content,
                published = post.published,
                selfLink = post.selfLink,
                title = post.title,
                updated = post.updated,
                url = post.url,
                labels = post.labels
            )
        }
        dao.insertPosts(entities)
    }

    suspend fun insertPages(pages: List<Page>) {
        val entities = pages.map { page ->
            BloggerPageEntity(
                id = page.id ?: "",
                author = page.author,
                content = page.content,
                published = page.published,
                selfLink = page.selfLink,
                title = page.title,
                updated = page.updated,
                url = page.url
            )
        }
        dao.insertPages(entities)
    }

    suspend fun insertComments(postId: String, comments: List<Comment>) {
        dao.deleteCommentsForPost(postId)
        val entities = comments.map { comment ->
            BloggerCommentEntity(
                id = comment.id ?: "",
                postId = postId,
                name = comment.name,
                profileImage = comment.profileImage,
                published = comment.published,
                comment = comment.comment
            )
        }
        dao.insertComments(entities)
    }

    private fun mapFromEntity(entity: BloggerPostEntity): Post {
        return Post(
            author = entity.author,
            content = entity.content,
            id = entity.id,
            published = entity.published,
            selfLink = entity.selfLink,
            title = entity.title,
            updated = entity.updated,
            url = entity.url,
            labels = entity.labels
        )
    }

    private fun mapPageFromEntity(entity: BloggerPageEntity): Page {
        return Page(
            author = entity.author,
            content = entity.content,
            id = entity.id,
            published = entity.published,
            selfLink = entity.selfLink,
            title = entity.title,
            updated = entity.updated,
            url = entity.url
        )
    }

    suspend fun getPosts(startIndex: String = "1") = withContext(Dispatchers.IO) {
        try {
            val response = api.getPosts(startIndex = startIndex)
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entries = doc.select("entry")
            val posts = entries.map { parsePost(it) }

            val nextToken = if (entries.size < DATA.MAX_POST_RESULTS.toInt()) {
                "end"
            } else {
                (startIndex.toInt() + entries.size).toString()
            }
            Resource.Success(BloggerResponse(items = posts, nextPageToken = nextToken))
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun searchPosts(query: String, startIndex: String = "1") = withContext(Dispatchers.IO) {
        try {
            val response = api.searchPosts(query = query, startIndex = startIndex)
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entries = doc.select("entry")
            val posts = entries.map { parsePost(it) }

            val nextToken = if (entries.size < DATA.MAX_POST_RESULTS.toInt()) {
                "end"
            } else {
                (startIndex.toInt() + entries.size).toString()
            }
            Resource.Success(BloggerResponse(items = posts, nextPageToken = nextToken))
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getPages() = withContext(Dispatchers.IO) {
        try {
            val response = api.getPages()
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entries = doc.select("entry")
            Resource.Success(entries.map { parsePage(it) })
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getPostDetails(postId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getPostDetails(postId)
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entry = doc.selectFirst("entry")
            if (entry != null) {
                Resource.Success(parsePost(entry))
            } else {
                Resource.Error("Post not found")
            }
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getPageDetails(pageId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getPageDetails(pageId)
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entry = doc.selectFirst("entry")
            if (entry != null) {
                Resource.Success(parsePage(entry))
            } else {
                Resource.Error("Page not found")
            }
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getComments(postId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getComments(postId)
            val doc = Jsoup.parse(response, "", Parser.xmlParser())
            val entries = doc.select("entry")
            val comments = entries.map { entry ->
                val id = entry.selectFirst("id")?.text()?.split("-")?.last() ?: ""
                val published = entry.selectFirst("published")?.text() ?: ""
                val content = entry.selectFirst("content")?.text() ?: ""
                val displayName = entry.select("author name").first()?.text() ?: DATA.UNKNOWN
                CommentItem(
                    id = id,
                    published = published,
                    content = content,
                    author = Author(displayName = displayName)
                )
            }
            Resource.Success(comments)
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    private fun parsePost(entry: Element): Post {
        val id = entry.selectFirst("id")?.text()?.split("-")?.last() ?: ""
        val title = entry.selectFirst("title")?.text() ?: ""
        val content = entry.selectFirst("content")?.text() ?: ""
        val published = entry.selectFirst("published")?.text() ?: ""
        val updated = entry.selectFirst("updated")?.text() ?: ""
        val urlPath = entry.selectFirst("link[rel=alternate]")?.attr("href") ?: ""
        val selfLink = entry.selectFirst("link[rel=self]")?.attr("href") ?: ""
        val authorName = entry.select("author name").first()?.text() ?: DATA.UNKNOWN
        val labels = entry.select("category").mapNotNull { it.attr("term").takeIf { term -> term.isNotEmpty() } }

        return Post(
            author = Author(displayName = authorName),
            content = content,
            id = id,
            published = published,
            selfLink = selfLink,
            title = title,
            updated = updated,
            url = urlPath,
            labels = labels
        )
    }

    private fun parsePage(entry: Element): Page {
        val id = entry.selectFirst("id")?.text()?.split("-")?.last() ?: ""
        val title = entry.selectFirst("title")?.text() ?: ""
        val content = entry.selectFirst("content")?.text() ?: ""
        val published = entry.selectFirst("published")?.text() ?: ""
        val updated = entry.selectFirst("updated")?.text() ?: ""
        val urlPath = entry.selectFirst("link[rel=alternate]")?.attr("href") ?: ""
        val selfLink = entry.selectFirst("link[rel=self]")?.attr("href") ?: ""
        val authorName = entry.select("author name").first()?.text() ?: DATA.UNKNOWN
        return Page(
            author = Author(displayName = authorName),
            content = content,
            id = id,
            published = published,
            selfLink = selfLink,
            title = title,
            updated = updated,
            url = urlPath
        )
    }
}