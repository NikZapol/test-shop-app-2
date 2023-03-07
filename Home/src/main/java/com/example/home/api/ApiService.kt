package com.example.home.api

import com.example.home.helper.Constants
import com.example.home.models.LatestResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET(Constants.END_POINT)
    suspend fun getLatest():Response<LatestResponse>

}