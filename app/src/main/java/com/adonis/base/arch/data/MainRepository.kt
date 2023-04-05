package com.adonis.base.arch.data

import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject
constructor(private val apiService: ApiService) : RepositoryService {

    override suspend fun getJoke(): Flow<ResponseHandler<Joke>> = flow{
        Timber.e("Im here")
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