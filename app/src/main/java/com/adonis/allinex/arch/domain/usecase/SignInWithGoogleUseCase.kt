package com.adonis.allinex.arch.domain.usecase

import com.adonis.allinex.arch.data.firebase.FirebaseRepositoryService
import com.adonis.allinex.util.ResponseHandler
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val firebaseRepositoryService: FirebaseRepositoryService
) {

    suspend operator fun invoke(signInCredential: SignInCredential): Flow<ResponseHandler<Boolean>> {
        return firebaseRepositoryService.signInUserWithGoogleFirebase(signInCredential)
    }

}