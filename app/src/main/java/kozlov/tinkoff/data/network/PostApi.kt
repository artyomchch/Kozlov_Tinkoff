package kozlov.tinkoff.data.network

import kozlov.tinkoff.data.network.pojo.PostDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/random")
    fun getRandomPost(
        @Query(QUERY_PARAM_JSON) json: String = JSON
    ): PostDTO

    @GET("/latest/0")
    fun getLatestPosts(
        @Query(QUERY_PARAM_JSON) json: String = JSON,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: String = PAGE_SIZE,
    ): PostDTO

    @GET("/top/0")
    fun getTopPosts(
        @Query(QUERY_PARAM_JSON) json: String = JSON,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: String = PAGE_SIZE,
        ): PostDTO

    companion object {
        private const val QUERY_PARAM_JSON = "json"
        private const val QUERY_PARAM_PAGE_SIZE = "pageSize"


        private const val JSON = "true"
        private const val PAGE_SIZE = "20"
    }

}