package kozlov.tinkoff.domain.repository

import kozlov.tinkoff.domain.entity.PostItem

interface PostRepository {

    suspend fun getLatestPost(page: Int): List<PostItem>

    suspend fun getTopPost(page: Int): List<PostItem>

    suspend fun getRandomPost(): PostItem
}