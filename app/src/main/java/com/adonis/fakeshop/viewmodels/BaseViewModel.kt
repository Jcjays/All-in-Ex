package com.adonis.fakeshop.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val mutableErrorHandlingMutableLiveData = MutableLiveData<String>()
    val mutableLoading = MutableLiveData<Boolean>()



}