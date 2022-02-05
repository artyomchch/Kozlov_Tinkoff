package kozlov.tinkoff.domain.entity

data class PostItem(
    private val image: String,
    private val description: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
