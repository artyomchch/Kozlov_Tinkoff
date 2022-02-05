package kozlov.tinkoff.domain.repository

interface PostRepository {

    suspend fun getLatestPost()

    suspend fun getBestPost()

    suspend fun getHotPost()
}