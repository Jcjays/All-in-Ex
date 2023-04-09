package com.adonis.base.arch.data.firebase

import com.adonis.base.util.ResponseHandler
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryService {

    suspend fun signInUserWithFirebase(signInCredential: SignInCredential) : Flow<ResponseHandler<Boolean>>

}