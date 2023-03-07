package com.example.home.repository

import com.example.home.api.ApiService
import javax.inject.Inject

class LatestRepository
    @Inject
    constructor(private val apiService: ApiService) {
       suspend fun getLatest() = apiService.getLatest()


}