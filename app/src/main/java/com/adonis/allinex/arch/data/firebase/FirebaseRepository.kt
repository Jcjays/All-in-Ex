package com.adonis.allinex.arch.data.firebase

import com.adonis.allinex.util.Constants
import com.adonis.allinex.util.ResponseHandler
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val googleOneTapClient: SignInClient,
    private val facebookLoginManager: LoginManager,
) : FirebaseRepositoryService {

    override suspend fun signInUserWithGoogleFirebase(signInCredential: SignInCredential): Flow<ResponseHandler<Boolean>> =
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

    override suspend fun signInUserWithFacebookFirebase(loginResult: LoginResult): Flow<ResponseHandler<Boolean>> =
        flow {
            emit(ResponseHandler.Loading())

            val token = loginResult.accessToken.token
            val credential = FacebookAuthProvider.getCredential(token)

            runCatching {
                auth.signInWithCredential(credential).await()
            }.onSuccess {
                val isNewUser = it.additionalUserInfo?.isNewUser ?: false

                if (isNewUser)
                    addUserToFireStore()

                emit(ResponseHandler.Success(true))
            }.onFailure {
                emit(ResponseHandler.Exception(it))
            }
        }

    override suspend fun signOut(): Flow<ResponseHandler<Boolean>> = flow {
        emit(ResponseHandler.Loading())

        runCatching {
            auth.signOut()
            googleOneTapClient.signOut()
            facebookLoginManager.logOut()
        }.onSuccess {
            emit(ResponseHandler.Success(true))
        }.onFailure {
            emit(ResponseHandler.Exception(it))
        }
    }

    override suspend fun getCurrentUser(): Flow<ResponseHandler<FirebaseUser>> = flow {
        emit(ResponseHandler.Loading())

        runCatching {
            auth.currentUser
        }.onSuccess { user ->
            if (user != null)
                emit(ResponseHandler.Success(user))
            else
                emit(ResponseHandler.Error(-1, "Cannot Detect Current User"))
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
