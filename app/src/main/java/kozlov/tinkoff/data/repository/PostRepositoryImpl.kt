package kozlov.tinkoff.data.repository

import android.util.Log
import kozlov.tinkoff.data.network.RetrofitInstance
import kozlov.tinkoff.domain.entity.PostItem
import kozlov.tinkoff.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor() : PostRepository {


    override suspend fun getLatestPost(page: Int): List<PostItem> {
        Log.d("network LATEST", RetrofitInstance.api.getLatestPosts(id = page).toString())
        return TODO()
    }


    override suspend fun getTopPost(page: Int): List<PostItem> {
        Log.d("network TOP", RetrofitInstance.api.getTopPosts(id = page).toString())
        return TODO()
    }

    override suspend fun getRandomPost(): PostItem {
        Log.d("network RAND", RetrofitInstance.api.getRandomPost().toString())
        return TODO()
    }
}