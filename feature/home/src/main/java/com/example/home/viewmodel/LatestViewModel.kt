package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.models.Latest
import com.example.home.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class LatestViewModel
    @Inject
    constructor
        (private val repository: Repository):ViewModel() {

            private val _response = MutableLiveData<List<Latest>>()

            val responseLatest:LiveData<List<Latest>>
                get() = _response

            init {
                getAllLatest()
            }

    private fun getAllLatest() = viewModelScope.launch {
        repository.getLatest().let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body()?.latest)
            }else{
                Log.d("tag","getAllLatest Error: ${response.code()}")
            }
        }
    }





}