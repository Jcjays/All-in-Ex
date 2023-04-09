package com.adonis.base.arch.domain.usecase

import com.adonis.base.arch.data.firebase.AuthRepositoryService
import com.adonis.base.util.ResponseHandler
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepositoryService: AuthRepositoryService
) {

    suspend operator fun invoke(signInCredential: SignInCredential): Flow<ResponseHandler<Boolean>> {
        return authRepositoryService.signInUserWithFirebase(signInCredential)
    }

}