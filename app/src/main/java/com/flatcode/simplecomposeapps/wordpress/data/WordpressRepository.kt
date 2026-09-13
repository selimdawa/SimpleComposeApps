package com.flatcode.simplecomposeapps.wordpress.data

import com.flatcode.simplecomposeapps.wordpress.data.network.WordPressApi
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.model.Rendered
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordpressRepository @Inject constructor(
    private val api: WordPressApi,
    private val postDao: PostDao
) {
    fun getAllPosts(): Flow<List<Post>> = postDao.getAllPosts().map { entities ->
        entities.map { mapFromEntity(it) }
    }

    private suspend fun getFavoriteIds(): Set<Int> = postDao.getFavoriteIds().toSet()

    suspend fun syncPosts() = coroutineScope {
        val posts = api.getPosts()
        val favoriteIds = getFavoriteIds()

        val postsWithMedia = posts.map { post ->
            async {
                if (post.featuredMedia > 0) {
                    try {
                        val media = api.getPostThumbnail(post.featuredMedia)
                        post.copy(featuredMediaUrl = media.guid?.rendered)
                    } catch (_: Exception) {
                        post
                    }
                } else {
                    post
                }
            }
        }.awaitAll()

        val entities = postsWithMedia.map { post ->
            mapToEntity(post, favoriteIds.contains(post.id))
        }

        postDao.insertPosts(entities)
    }

    suspend fun toggleFavorite(postId: Int) {
        val isFav = postDao.isFavorite(postId).first()
        postDao.updateFavorite(postId, !isFav)
    }

    private fun mapFromEntity(entity: PostEntity): Post {
        return Post(
            id = entity.wpPostId,
            title = Rendered(rendered = entity.wpTitle),
            excerpt = Rendered(rendered = entity.wpExcerpt),
            content = Rendered(rendered = entity.wpContent),
            featuredMedia = entity.featuredMedia,
            featuredMediaUrl = entity.featuredMediaUrl,
            isFavorite = entity.isFavorite
        )
    }

    private fun mapToEntity(post: Post, isFavorite: Boolean): PostEntity {
        return PostEntity(
            wpPostId = post.id,
            wpTitle = post.title?.rendered,
            wpExcerpt = post.excerpt?.rendered,
            wpContent = post.content?.rendered,
            featuredMedia = post.featuredMedia,
            featuredMediaUrl = post.featuredMediaUrl,
            isFavorite = isFavorite
        )
    }
}
