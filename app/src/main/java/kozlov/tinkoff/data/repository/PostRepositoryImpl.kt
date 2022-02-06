package kozlov.tinkoff.data.repository

import android.util.Log
import kozlov.tinkoff.data.mapper.PostItemMapper
import kozlov.tinkoff.data.network.PostApi
import kozlov.tinkoff.data.network.RetrofitInstance
import kozlov.tinkoff.domain.entity.PostItem
import kozlov.tinkoff.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val mapper: PostItemMapper,
    private val retrofit: PostApi

) : PostRepository {

    private var randomListPost = listOf<PostItem>()
    private var latestListPost = listOf<PostItem>()
    private var topListPost = listOf<PostItem>()

    override suspend fun getLatestPost(page: Int): List<PostItem> {
        Log.d("network LATEST", RetrofitInstance.api.getLatestPosts(id = page).toString())
        latestListPost = latestListPost.plus(mapper.mapListNetworkModelToListEntityPost(retrofit.getLatestPosts(page).result))
        return latestListPost
    }


    override suspend fun getTopPost(page: Int): List<PostItem> {
        Log.d("network TOP", RetrofitInstance.api.getTopPosts(id = page).toString())
        topListPost = topListPost.plus(mapper.mapListNetworkModelToListEntityPost(retrofit.getTopPosts(id = page).result))
        return topListPost
    }

    override suspend fun getRandomPost(): List<PostItem>{
        Log.d("network RAND", RetrofitInstance.api.getRandomPost().toString())
        randomListPost = randomListPost.plus(mapper.mapNetworkModelToEntityPost(retrofit.getRandomPost()))
        return randomListPost
    }
}