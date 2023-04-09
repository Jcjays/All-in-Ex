package com.adonis.base.arch.data.retrofit

import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject
constructor(private val apiService: ApiService) : MainRepositoryService {

    override suspend fun getJoke(): Flow<ResponseHandler<Joke>> = flow{
        runCatching {
            emit(ResponseHandler.Loading())
            apiService.getJoke()
        }.onSuccess { response ->
            if (response.isSuccessful && response.body() != null) {
                emit(ResponseHandler.Success(response.body()!!))
            } else {
                emit(ResponseHandler.Error(response.code()))
            }
        }.onFailure { ex ->
            emit(ResponseHandler.Exception(ex))
        }
    }

}