package com.adonis.allinex.arch.domain.usecase

import com.adonis.allinex.arch.data.firebase.FirebaseRepositoryService
import com.adonis.allinex.util.ResponseHandler
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val firebaseRepositoryService: FirebaseRepositoryService
) {

    suspend operator fun invoke(): Flow<ResponseHandler<FirebaseUser>> {
        return firebaseRepositoryService.getCurrentUser()
    }

}