package com.adonis.fakeshop.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

suspend fun <T : Any> safeApiCall( //handle request
    execute: suspend () -> Response<T>
): ResponseHandler<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ResponseHandler.Success(body)
            } else {
                ResponseHandler.Error(response.code(), response.message())
            }
        } catch (e: HttpException) {
            ResponseHandler.Error(e.code(), e.message())
        } catch (e: IOException) {
            Timber.e("No internet connectivity")
            ResponseHandler.Error(-1, "Please check your internet connectivity.")
        } catch (e: Throwable) {
            Timber.tag("Response handler").e(e.message.toString())
            ResponseHandler.Exception(e, "Something went wrong.")
        }
    }
}

