package com.adonis.allinex.arch.data.firebase

import com.adonis.allinex.util.ResponseHandler
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow

interface FirebaseRepositoryService {

    suspend fun signInUserWithGoogleFirebase(signInCredential: SignInCredential) : Flow<ResponseHandler<Boolean>>

    suspend fun signInUserWithFacebookFirebase(loginResult: LoginResult) : Flow<ResponseHandler<Boolean>>

}