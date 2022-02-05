package kozlov.tinkoff.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.POST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PostApi by lazy {
        retrofit.create(PostApi::class.java)
    }
}