package com.adonis.allinex.util

sealed class ResponseHandler<out T>{
    class Loading<T> : ResponseHandler<T>()
    class Success<T>(val data: T) : ResponseHandler<T>()
    class Error<T>(val code: Int, val message: String? = "") : ResponseHandler<T>()
    class Exception<T>(val e: Throwable, val message: String? = "") : ResponseHandler<T>()
}
