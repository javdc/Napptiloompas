package com.javdc.napptiloompas.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javdc.napptiloompas.common.di.IoDispatcher
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCase
import com.javdc.napptiloompas.domain.util.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OompaLoompaDetailsViewModel @Inject constructor(
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
    private val getOompaLoompaDetailsUseCase: GetOompaLoompaDetailsUseCase,
) : ViewModel() {

    private val _oompaLoompaDetailsLiveData = MutableLiveData<AsyncResult<OompaLoompaBo>>()
    val oompaLoompaDetailsLiveData: LiveData<AsyncResult<OompaLoompaBo>>
        get() = _oompaLoompaDetailsLiveData

    fun fetchOompaLoompaDetails(id: Long) {
        viewModelScope.launch(iODispatcher) {
            getOompaLoompaDetailsUseCase(id).collect { result ->
                _oompaLoompaDetailsLiveData.postValue(result)
            }
        }
    }

}