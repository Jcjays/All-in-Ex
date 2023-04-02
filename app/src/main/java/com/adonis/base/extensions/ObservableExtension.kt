package com.adonis.base.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

    
fun <T> Flow<T>.runOnIODispatcher() : Flow<T>{
    return this.flowOn(Dispatchers.IO)
}



