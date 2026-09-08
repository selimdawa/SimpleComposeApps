package com.flatcode.simplecomposeapps.wordpress.utils

import com.flatcode.simplecomposeapps.wordpress.model.Media
import com.flatcode.simplecomposeapps.wordpress.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WPApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPosts(): List<Post> {
        return client.get("https://techcrunch.com/wp-json/wp/v2/posts?per_page=20").body()
    }

    suspend fun getPostThumbnail(id: Int): Media {
        return client.get("https://techcrunch.com/wp-json/wp/v2/media/$id").body()
    }
}
