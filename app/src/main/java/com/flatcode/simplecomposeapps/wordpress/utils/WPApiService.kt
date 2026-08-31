package com.flatcode.simplecomposeapps.wordpress.utils

import com.flatcode.simplecomposeapps.wordpress.model.Media
import com.flatcode.simplecomposeapps.wordpress.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface WPApiService {
    @GET("posts?per_page=20")
    suspend fun getPosts(): List<Post>

    @GET("media/{id}")
    suspend fun getPostThumbnail(@Path("id") id: Int): Media
}