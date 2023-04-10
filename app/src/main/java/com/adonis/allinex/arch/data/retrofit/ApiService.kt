package com.adonis.allinex.arch.data.retrofit

import com.adonis.allinex.arch.model.remote.Joke
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getJoke() : Response<Joke>

}