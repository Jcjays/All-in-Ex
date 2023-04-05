package com.adonis.base.arch.data

import com.adonis.base.arch.model.remote.Joke
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getJoke() : Response<Joke>

}