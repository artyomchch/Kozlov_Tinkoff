package kozlov.tinkoff.presentation

import android.util.Log
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
) : ViewModel() {



    var positionRandomItem = 0
    var finishPositionRandom: Int = 0

    private val _randomItemList = MutableLiveData<List<PostItem>>()
    val randomItemList: LiveData<List<PostItem>>
        get() = _randomItemList


    var positionLatestItem = 0
    var finishPositionLatest: Int = 19
    var pageLatest = 0

    private val _latestItem = MutableLiveData<List<PostItem>>()
    val latestItem: LiveData<List<PostItem>>
        get() = _latestItem

    var positionTopItem = 0
    var finishPositionTop: Int = 0

    private val _topItem = MutableLiveData<List<PostItem>>()
    val topItem: LiveData<List<PostItem>>
        get() = _topItem

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _categoryState = MutableLiveData<Int>()
    val categoryState: LiveData<Int>
        get() = _categoryState


    init {

        getRandomPost()
        getLatestPosts(20)
        _categoryState.value = 0
    }

    fun getRandomPost() {
        _loadingState.value = true
        viewModelScope.launch {
            _randomItemList.value = getPostItemRandomUseCase.invoke()
        }
    }



    fun getLatestPosts(page: Int) {
        _loadingState.value = true
        viewModelScope.launch {
            _latestItem.value = getPostItemLatestUseCase.invoke(page)
        }
    }

    fun getTopPosts(page: Int) {
        viewModelScope.launch {
            _topItem.value = getPostItemTopUseCase.invoke(page)
        }
    }

    fun setPositionCategory(position: Int) {
        _categoryState.value = position
    }
}