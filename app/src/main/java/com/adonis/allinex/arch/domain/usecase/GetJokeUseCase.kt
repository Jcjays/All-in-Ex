package com.adonis.allinex.arch.domain.usecase

import com.adonis.allinex.arch.data.retrofit.MainRepositoryService
import com.adonis.allinex.arch.model.remote.Joke
import com.adonis.allinex.util.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(
    private val mainRepositoryService: MainRepositoryService
) {

    suspend operator fun invoke() : Flow<ResponseHandler<Joke>> {
        return mainRepositoryService.getJoke()
    }

}