package kozlov.tinkoff.domain.usecases

import kozlov.tinkoff.domain.entity.PostItem
import kozlov.tinkoff.domain.repository.PostRepository
import javax.inject.Inject

class GetPostItemTopUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(page: Int): List<PostItem> = postRepository.getTopPost(page)
}