package com.ismin.csproject

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE

interface FountainService {

    @GET("fountains")
    fun getAllFountains(): Call<List<Fountain>>

    @POST("fountains")
    fun createFountain(@Body ftn: Fountain): Call<Fountain>

    @GET("fountains/{id}")
    fun getFountain(@Path("id") id: String)

    @DELETE("fountains/{id}")
    fun deleteFountain(@Path("id") id: String)
}