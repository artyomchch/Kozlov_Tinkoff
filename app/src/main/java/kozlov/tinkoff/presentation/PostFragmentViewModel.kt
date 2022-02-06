package kozlov.tinkoff.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kozlov.tinkoff.domain.entity.PostItem
import kozlov.tinkoff.domain.usecases.GetPostItemLatestUseCase
import kozlov.tinkoff.domain.usecases.GetPostItemRandomUseCase
import kozlov.tinkoff.domain.usecases.GetPostItemTopUseCase
import javax.inject.Inject

class PostFragmentViewModel @Inject constructor(
    private val getPostItemLatestUseCase: GetPostItemLatestUseCase,
    private val getPostItemRandomUseCase: GetPostItemRandomUseCase,
    private val getPostItemTopUseCase: GetPostItemTopUseCase
): ViewModel() {


    private val _randomItem = MutableLiveData<PostItem>()
    val randomItem: LiveData<PostItem>
        get() = _randomItem

    private val _latestItem = MutableLiveData<List<PostItem>>()
    val latestItem: LiveData<List<PostItem>>
        get() = _latestItem

    private val _topItem = MutableLiveData<List<PostItem>>()
    val topItem: LiveData<List<PostItem>>
        get() = _topItem

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    init {
        getRandomPost()
    }

    fun getRandomPost() {
        _loadingState.value = true
        viewModelScope.launch {
            _randomItem.value = getPostItemRandomUseCase.invoke()
        }
       // _loadingState.value = false
    }

    fun getLatestPosts(page: Int) {
        _loadingState.value = true
        viewModelScope.launch {
            _latestItem.value = getPostItemLatestUseCase.invoke(page)
        }
       // _loadingState.value = false
    }

    fun getTopPosts(page: Int) {
        viewModelScope.launch {
            _topItem.value = getPostItemTopUseCase.invoke(page)
        }
    }
}