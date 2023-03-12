package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.models.Sale
import com.example.home.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class SaleViewModel
    @Inject
    constructor
        (private val repository: Repository):ViewModel() {

            private val _response = MutableLiveData<List<Sale>>()

            val responseSale:LiveData<List<Sale>>
                get() = _response

            init {
                getAllSale()
            }

    private fun getAllSale() = viewModelScope.launch {
        repository.getSale().let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body()?.flash_sale)
            }else{
                Log.d("tag","getAllSale Error: ${response.code()}")
            }
        }
    }





}