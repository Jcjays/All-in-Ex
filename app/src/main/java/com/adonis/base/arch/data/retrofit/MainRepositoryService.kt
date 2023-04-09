package com.adonis.base.arch.data.retrofit

import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow


interface MainRepositoryService {

    suspend fun getJoke() : Flow<ResponseHandler<Joke>>

}