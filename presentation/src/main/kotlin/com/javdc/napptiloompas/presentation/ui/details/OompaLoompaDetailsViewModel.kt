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

    fun fetchOompaLoompaDetails(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getOompaLoompaDetailsUseCase(id).collect { result ->
                _oompaLoompaDetailsLiveData.postValue(result)
            }
        }
    }

}