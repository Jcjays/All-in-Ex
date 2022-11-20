package com.adonis.fakeshop.extensions

import com.adonis.fakeshop.util.ResponseHandler
import com.adonis.fakeshop.viewmodels.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


suspend fun <T> ResponseHandler<T>.fetchResponse(
    baseViewModel: BaseViewModel,
    onResponse: suspend (T) -> Unit
) {

    baseViewModel.mutableLoading.value = true

    val handleResponse = when(this){
        is ResponseHandler.Error -> baseViewModel.mutableErrorHandlingMutableLiveData.postValue(this.message)
        is ResponseHandler.Exception -> baseViewModel.mutableErrorHandlingMutableLiveData.postValue(this.message)
        is ResponseHandler.Success ->{
            onResponse(this.data)
        }
    }

    //turn off the loading
    baseViewModel.mutableLoading.value = false

    return handleResponse
}

    
fun <T> Flow<T>.runOnIODispatcher() : Flow<T>{
    return this.flowOn(Dispatchers.IO)
}



