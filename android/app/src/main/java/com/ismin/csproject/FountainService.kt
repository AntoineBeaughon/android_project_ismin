package com.ismin.csproject

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FountainService {

    @GET("fountains")
    fun getAllFountains(): Call<List<Book>>

    @POST("fountains")
    fun createFountain(@Body book: Book): Call<Book>
}