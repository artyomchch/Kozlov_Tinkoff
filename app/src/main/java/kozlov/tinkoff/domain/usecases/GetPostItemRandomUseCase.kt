package kozlov.tinkoff.domain.usecases

import kozlov.tinkoff.domain.entity.PostItem
import kozlov.tinkoff.domain.repository.PostRepository
import javax.inject.Inject

class GetPostItemRandomUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(): List<PostItem> = postRepository.getRandomPost()
}