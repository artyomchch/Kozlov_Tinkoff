package kozlov.tinkoff.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import kozlov.tinkoff.data.network.PostApi
import kozlov.tinkoff.data.network.RetrofitInstance
import kozlov.tinkoff.data.repository.PostRepositoryImpl
import kozlov.tinkoff.domain.repository.PostRepository

@Module
interface PostDomainModule {

    @ApplicationScope
    @Binds
    fun bindPostRepository(impl: PostRepositoryImpl): PostRepository

    companion object{

        @ApplicationScope
        @Provides
        fun provideNetworkData(): PostApi{
            return RetrofitInstance.api
        }
    }
}