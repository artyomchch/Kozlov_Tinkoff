package kozlov.tinkoff.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kozlov.tinkoff.presentation.PostFragmentViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(PostFragmentViewModel::class)
    @Binds
    fun bindAddFragmentViewModel(impl: PostFragmentViewModel): ViewModel



}