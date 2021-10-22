package com.carkzis.pomona.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carkzis.pomona.ui.DomainFruit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FruitDetailViewModel @Inject constructor() : ViewModel() {

    private var _fruit = MutableLiveData<DomainFruit>()
    val fruit: LiveData<DomainFruit>
        get() = _fruit

    /**
     * Posts the details of the selected fruit to the fruit LiveData.
     */
    fun setUpFruitDetail(selectedFruit: DomainFruit) {
        _fruit.value = selectedFruit
    }

}