package com.flatcode.simplecomposeapps.blogger.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BloggerDao {
    // Posts
    @Query("SELECT * FROM blogger_posts")
    fun getAllPosts(): Flow<List<BloggerPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<BloggerPostEntity>)

    @Query("SELECT * FROM blogger_posts WHERE id = :id")
    fun getPostById(id: String): Flow<BloggerPostEntity?>

    // Pages
    @Query("SELECT * FROM blogger_pages")
    fun getAllPages(): Flow<List<BloggerPageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPages(pages: List<BloggerPageEntity>)

    @Query("SELECT * FROM blogger_pages WHERE id = :id")
    fun getPageById(id: String): Flow<BloggerPageEntity?>

    // Comments
    @Query("SELECT * FROM blogger_comments WHERE postId = :postId")
    fun getCommentsForPost(postId: String): Flow<List<BloggerCommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<BloggerCommentEntity>)

    @Query("DELETE FROM blogger_comments WHERE postId = :postId")
    suspend fun deleteCommentsForPost(postId: String)
}
