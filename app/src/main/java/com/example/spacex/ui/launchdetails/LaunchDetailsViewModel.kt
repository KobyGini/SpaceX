package com.example.spacex.ui.launchdetails

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.example.spacex.model.Launch
import com.example.spacex.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel
@Inject
constructor(
    private val repository: DataRepository
): ViewModel() {

    private var _launch = MutableLiveData<Launch>()
    val launch : LiveData<Launch> = _launch

    @ExperimentalPagingApi
    fun getLaunchesByIdLive(id:String){
        viewModelScope.launch {
            _launch.value = repository.getLaunchDataById(id)
        }
    }
}