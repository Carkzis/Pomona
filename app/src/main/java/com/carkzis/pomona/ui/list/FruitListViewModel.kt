package com.carkzis.pomona.ui.list

import androidx.lifecycle.*
import com.carkzis.pomona.data.Repository
import com.carkzis.pomona.ui.DomainFruit
import com.carkzis.pomona.util.Event
import com.carkzis.pomona.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FruitListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val fruitList: LiveData<List<DomainFruit>?> = repository.getFruit().asLiveData()

    private var _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    init {
        refreshRepository()
    }

    fun refreshRepository() {
        viewModelScope.launch {
            repository.refreshFruitData().collect { loadingState ->
                when (loadingState) {
                    is LoadingState.Loading -> {
                        Timber.e("Loading reviews...")
                        _loadingState.value = loadingState
                    }
                    is LoadingState.Success -> {
                        Timber.e("Reviews loaded! ${loadingState.dataSize} of them.")
                        Timber.e(fruitList.value.toString())
                        showToastMessage(loadingState.message)
                        Timber.e(fruitList.value.toString())
                        _loadingState.value = loadingState
                    }
                    is LoadingState.Error -> {
                        Timber.e("Error loading reviews: ${loadingState.exception}.")
                        showToastMessage(loadingState.message)
                        _loadingState.value = loadingState
                    }
                }
            }
        }
    }

    private var _toastText = MutableLiveData<Event<Int>>()
    val toastText: LiveData<Event<Int>>
        get() = _toastText

    /**
     * Add the toast String ID into an Event class and post it to the _toastText LiveData.
     */
    private fun showToastMessage(message: Int) {
        _toastText.value = Event(message)
    }

}