package com.adonis.allinex.arch.data.retrofit

import com.adonis.allinex.arch.model.remote.Joke
import com.adonis.allinex.util.ResponseHandler
import kotlinx.coroutines.flow.Flow


interface MainRepositoryService {

    suspend fun getJoke() : Flow<ResponseHandler<Joke>>

}