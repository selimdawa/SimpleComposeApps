package com.flatcode.simplecomposeapps.wordpress.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM posts WHERE wpPostId = :postId AND isFavorite = 1)")
    fun isFavorite(postId: Int): Flow<Boolean>

    @Query("SELECT wpPostId FROM posts WHERE isFavorite = 1")
    suspend fun getFavoriteIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts WHERE wpPostId = :postId")
    suspend fun deletePost(postId: Int)

    @Query("DELETE FROM posts WHERE isFavorite = 0")
    suspend fun deleteNonFavorites()

    @Query("UPDATE posts SET isFavorite = :isFav WHERE wpPostId = :postId")
    suspend fun updateFavorite(postId: Int, isFav: Boolean)
}