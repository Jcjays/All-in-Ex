package com.adonis.base.arch.data.firebase

import com.adonis.base.util.Constants
import com.adonis.base.util.ResponseHandler
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : AuthRepositoryService {

    override suspend fun signInUserWithFirebase(signInCredential: SignInCredential): Flow<ResponseHandler<Boolean>> =
        flow {
            emit(ResponseHandler.Loading())

            val token = signInCredential.googleIdToken
            val googleCredential = GoogleAuthProvider.getCredential(token, null)

            runCatching {
                auth.signInWithCredential(googleCredential).await()
            }.onSuccess {
                val isNewUser = it.additionalUserInfo?.isNewUser ?: false

                if (isNewUser)
                    addUserToFireStore()

                emit(ResponseHandler.Success(true))
            }.onFailure {
                emit(ResponseHandler.Exception(it))
            }
        }

    private suspend fun addUserToFireStore() {
        auth.currentUser?.apply {
            val user = hashMapOf(
                "name" to displayName,
                "email" to email,
                "profileImage" to photoUrl?.toString(),
                "createdAt" to serverTimestamp()
            )

            runCatching {
                db.collection(Constants.USERS_COLLECTION).document(uid).set(user).await()
            }.onSuccess {
                Timber.e("--> success writing in fireStore DB")
            }.onFailure {
                Timber.e("--> error occurred when writing in fireStore DB", it)
            }
        }
    }

}
