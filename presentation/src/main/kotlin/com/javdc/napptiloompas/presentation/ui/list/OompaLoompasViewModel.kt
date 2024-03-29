package com.javdc.napptiloompas.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javdc.napptiloompas.common.di.DefaultDispatcher
import com.javdc.napptiloompas.common.di.IoDispatcher
import com.javdc.napptiloompas.presentation.model.GenderEnum
import com.javdc.napptiloompas.presentation.model.ProfessionEnum
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCase
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.presentation.mapper.toVo
import com.javdc.napptiloompas.presentation.model.AppliedFiltersVo
import com.javdc.napptiloompas.presentation.model.OompaLoompaVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OompaLoompasViewModel @Inject constructor(
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val getOompaLoompasUseCase: GetOompaLoompasUseCase,
) : ViewModel() {

    private var oompaLoompas = mutableListOf<OompaLoompaVo>()
    private var currentPage = 0
    private var reachedLastPage: Boolean = false

    private val _oompaLoompasLiveData = MutableLiveData<AsyncResult<List<OompaLoompaVo>>>()
    val oompaLoompasLiveData: LiveData<AsyncResult<List<OompaLoompaVo>>>
        get() = _oompaLoompasLiveData

    private val _filtersLiveData = MutableLiveData(AppliedFiltersVo(emptyList(), emptyList()))
    val filtersLiveData: LiveData<AppliedFiltersVo>
        get() = _filtersLiveData

    init {
        fetchOompaLoompas()
    }

    fun fetchOompaLoompas() {
        if (reachedLastPage) return
        viewModelScope.launch(iODispatcher) {
            getOompaLoompasUseCase(currentPage + 1).collect { result ->
                when (result) {
                    is AsyncResult.Success -> {
                        currentPage = result.data.current
                        reachedLastPage = result.data.total <= currentPage
                        oompaLoompas.addAll(result.data.results.map { it.toVo() })
                        postFilteredOompaLoompas()
                    }

                    is AsyncResult.Error -> {
                        _oompaLoompasLiveData.postValue(AsyncResult.Error(error = result.error))
                    }

                    is AsyncResult.Loading -> {
                        _oompaLoompasLiveData.postValue(AsyncResult.Loading())
                    }
                }
            }
        }
    }

    fun setProfessionsFilter(professions: List<ProfessionEnum>) {
        _filtersLiveData.value = _filtersLiveData.value?.copy(professionsFilter = professions)
        postFilteredOompaLoompas()
    }

    fun setGendersFilter(genders: List<GenderEnum>) {
        _filtersLiveData.value = _filtersLiveData.value?.copy(gendersFilter = genders)
        postFilteredOompaLoompas()
    }

    private fun postFilteredOompaLoompas() {
        viewModelScope.launch(defaultDispatcher) {
            val professionsFilter = _filtersLiveData.value?.professionsFilter.orEmpty()
            val gendersFilter = _filtersLiveData.value?.gendersFilter.orEmpty()

            var filteredOompaLoompas = oompaLoompas.toList()
            if (professionsFilter.isNotEmpty()) filteredOompaLoompas = filteredOompaLoompas.filter { professionsFilter.contains(it.profession) }
            if (gendersFilter.isNotEmpty()) filteredOompaLoompas = filteredOompaLoompas.filter { gendersFilter.contains(it.gender) }
            _oompaLoompasLiveData.postValue(AsyncResult.Success(filteredOompaLoompas))
        }
    }

    fun retry() = fetchOompaLoompas()

}