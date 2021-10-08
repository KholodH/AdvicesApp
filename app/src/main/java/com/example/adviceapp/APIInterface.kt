package com.example.adviceapp

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("advice")
    fun getAll(): Call<RAdvice>
}