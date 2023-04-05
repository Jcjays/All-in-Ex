package com.adonis.base.arch.data

import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow


interface RepositoryService {

    suspend fun getJoke() : Flow<ResponseHandler<Joke>>

}