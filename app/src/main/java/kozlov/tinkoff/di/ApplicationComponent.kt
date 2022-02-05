package kozlov.tinkoff.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kozlov.tinkoff.presentation.PostFragment


@ApplicationScope
@Component(
    modules = [PostDomainModule::class, ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: PostFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

}