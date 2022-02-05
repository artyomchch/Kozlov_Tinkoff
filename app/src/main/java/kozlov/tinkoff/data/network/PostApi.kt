package kozlov.tinkoff.data.network

import kozlov.tinkoff.data.network.pojo.PostDto
import kozlov.tinkoff.data.network.pojo.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @GET("/random")
    suspend fun getRandomPost(
          @Query(QUERY_PARAM_JSON) json: String = JSON
    ): Result

    @GET("latest/{id}")
    suspend fun getLatestPosts(
        @Path("id") id: Int,
        @Query(QUERY_PARAM_JSON) json: String = JSON,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: String = PAGE_SIZE,
    ): Result

    @GET("top/{id}")
    suspend fun getTopPosts(
        @Path("id") id: Int,
        @Query(QUERY_PARAM_JSON) json: String = JSON,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: String = PAGE_SIZE,
    ): PostDto

    companion object {
        private const val QUERY_PARAM_JSON = "json"
        private const val QUERY_PARAM_PAGE_SIZE = "pageSize"


        private const val JSON = "true"
        private const val PAGE_SIZE = "20"
        private const val ID = "0"

    }

}