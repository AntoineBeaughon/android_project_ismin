package com.ismin.csproject

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface UserService {
    @GET("favorites")
    fun getFavorites(): Call<ArrayList<String>>

    @PUT("favorites")
    fun addFavorites(@Body() id: String): Call<ArrayList<String>>

    @DELETE("favorites/{id}")
    fun deleteFavorite(@Path("id") id: String): Call<ArrayList<String>>
}