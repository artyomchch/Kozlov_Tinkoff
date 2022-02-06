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

    private var list = listOf<PostItem>()

    override suspend fun getLatestPost(page: Int): List<PostItem> {
        Log.d("network LATEST", RetrofitInstance.api.getLatestPosts(id = page).toString())
        return mapper.mapListNetworkModelToListEntityPost(retrofit.getLatestPosts(page).result)
    }


    override suspend fun getTopPost(page: Int): List<PostItem> {
        Log.d("network TOP", RetrofitInstance.api.getTopPosts(id = page).toString())
        return mapper.mapListNetworkModelToListEntityPost(retrofit.getTopPosts(id = page).result)
    }

    override suspend fun getRandomPost(): List<PostItem>{
        Log.d("network RAND", RetrofitInstance.api.getRandomPost().toString())
        list = list.plus(mapper.mapNetworkModelToEntityPost(retrofit.getRandomPost()))
        return list
    }
}