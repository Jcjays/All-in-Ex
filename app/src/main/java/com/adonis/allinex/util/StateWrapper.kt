package com.adonis.allinex.util

data class StateWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val exception: String? = null,
)