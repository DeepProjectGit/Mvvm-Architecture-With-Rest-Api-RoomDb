package com.example.task2.data.api_service

import com.example.task2.data.resposnse.ResponseItem
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getUserList():List<ResponseItem?>
}