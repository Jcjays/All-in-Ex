package com.adonis.base.arch.domain.usecase

import com.adonis.base.arch.data.RepositoryService
import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(
    private val repositoryService: RepositoryService
) {

    suspend operator fun invoke() : Flow<ResponseHandler<Joke>> {
        return repositoryService.getJoke()
    }

}