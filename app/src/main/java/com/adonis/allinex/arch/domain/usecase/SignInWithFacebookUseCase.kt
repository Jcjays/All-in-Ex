package com.adonis.allinex.arch.domain.usecase

import com.adonis.allinex.arch.data.firebase.FirebaseRepositoryService
import com.adonis.allinex.util.ResponseHandler
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithFacebookUseCase @Inject constructor(
    private val firebaseRepositoryService: FirebaseRepositoryService
) {

    suspend operator fun invoke(loginResult: LoginResult): Flow<ResponseHandler<Boolean>> {
        return firebaseRepositoryService.signInUserWithFacebookFirebase(loginResult)
    }

}