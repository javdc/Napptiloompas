package com.javdc.napptiloompas.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCase
import com.javdc.napptiloompas.domain.util.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OompaLoompaDetailsViewModel @Inject constructor(
    private val getOompaLoompaDetailsUseCase: GetOompaLoompaDetailsUseCase,
) : ViewModel() {

    private val _oompaLoompaDetailsLiveData = MutableLiveData<AsyncResult<OompaLoompaBo>>()
    val oompaLoompaDetailsLiveData: LiveData<AsyncResult<OompaLoompaBo>>
        get() = _oompaLoompaDetailsLiveData

//    private val _stopFavoriteStatusLiveData = MutableLiveData<Boolean>()
//    val stopFavoriteStatusLiveData: LiveData<Boolean>
//        get() = _stopFavoriteStatusLiveData

    fun fetchOompaLoompaDetails(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getOompaLoompaDetailsUseCase(id).collect { result ->
                _oompaLoompaDetailsLiveData.postValue(result)
            }
        }
    }

//    fun observeFavoriteStatusFromLocal(code: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            isStopFavoriteUseCase(code).collect {
//                _stopFavoriteStatusLiveData.postValue(it)
//            }
//        }
//    }
//
//    fun addStopToFavorites(code: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            addStopToFavoritesUseCase(code)
//        }
//    }
//
//    fun removeStopFromFavorites(code: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            removeStopFromFavoritesUseCase(code)
//        }
//    }

}