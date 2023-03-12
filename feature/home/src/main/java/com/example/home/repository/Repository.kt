package com.example.home.repository

import com.example.home.api.ApiService
import javax.inject.Inject

class Repository
    @Inject
    constructor(private val apiService: ApiService) {
       suspend fun getLatest() = apiService.getLatest()
    suspend fun getSale() = apiService.getSale()


}