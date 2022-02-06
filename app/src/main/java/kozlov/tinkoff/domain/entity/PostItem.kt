package kozlov.tinkoff.domain.entity

data class PostItem(
    val image: String?,
    val description: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
