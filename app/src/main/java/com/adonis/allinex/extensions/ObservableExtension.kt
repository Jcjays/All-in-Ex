package com.adonis.allinex.extensions

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> Flow<T>.runOnIODispatcher(): Flow<T> {
    return this.flowOn(Dispatchers.IO)
}

fun Throwable.identifyRetrofitError(): String {
    return when (this) {
        is UnknownHostException -> "No internet connection"
        is SocketTimeoutException -> "Network connection timed out"
        is IOException -> "Network error occurred"
        is HttpException -> "HTTP error ${this.code()}: ${this.message()}"
        is JSONException -> "JSON parsing error"
        is IllegalArgumentException -> "Invalid argument passed to API"
        is NullPointerException -> "Response object or one of its fields is null"
        is OutOfMemoryError -> "Out of memory error"
        else -> return this.message.toString()
    }
}

fun Throwable.identifyFirebaseAuthError(): String {
    return when (this) {
        is FirebaseAuthInvalidCredentialsException -> "Invalid login credentials."
        is FirebaseAuthUserCollisionException -> "User with this email/phone number already exists."
        is FirebaseAuthInvalidUserException -> "This user account has been disabled or deleted."
        is FirebaseAuthRecentLoginRequiredException -> "Authentication state is no longer valid. Please log in again."
        else -> "An unknown error occurred. Please try again later."
    }
}




