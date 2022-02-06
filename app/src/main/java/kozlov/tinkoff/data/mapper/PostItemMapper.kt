package kozlov.tinkoff.data.mapper

import kozlov.tinkoff.data.network.pojo.Result
import kozlov.tinkoff.domain.entity.PostItem
import javax.inject.Inject

class PostItemMapper @Inject constructor() {

    fun mapNetworkModelToEntityPost(result: Result) = PostItem(
        image = result.gifURL,
        description = result.description
    )

    fun mapListNetworkModelToListEntityPost(list: List<Result>) =
        list.map {
            mapNetworkModelToEntityPost(it)
        }


}