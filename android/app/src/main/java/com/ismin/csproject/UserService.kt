package com.ismin.csproject

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE

interface UserService {
    @GET("users/{userId}/favorites")
    fun getFavorites(@Path("userId") userId: String): Call<ArrayList<String>>

    @POST("users/{userId/favorites")
    fun addFavorites(@Path("userId") userId: String, @Body() id: String): Call<ArrayList<String>>

    @DELETE("users/{userId}/favorites/{id}")
    fun deleteFavorite(@Path("userId") userId: String, @Path("id") id: String): Call<ArrayList<String>>
}