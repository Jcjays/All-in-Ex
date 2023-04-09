package com.adonis.base.arch.domain.usecase

import com.adonis.base.arch.data.retrofit.MainRepositoryService
import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(
    private val mainRepositoryService: MainRepositoryService
) {

    suspend operator fun invoke() : Flow<ResponseHandler<Joke>> {
        return mainRepositoryService.getJoke()
    }

}