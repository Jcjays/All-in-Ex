package com.adonis.base.ui.viewmodels

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adonis.base.MainActivity
import com.adonis.base.util.SingleLiveEvent
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val _showLoading = SingleLiveEvent<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _showError = SingleLiveEvent<String>()
    val showError: LiveData<String>
        get() = _showError

    private val _showNoInternetConnection = SingleLiveEvent<Unit>()
    val showNoInternetConnection: LiveData<Unit>
        get() = _showNoInternetConnection

    private val _showException = SingleLiveEvent<String>()
    val showException: LiveData<String>
        get() = _showException

    open fun observeCommonEvent(mainActivity: AppCompatActivity) {
        showLoading.observe(mainActivity){
            Timber.e("---> Loading")
        }
    }

    protected fun showLoading() {
        Timber.e("---> Mwa")
        _showLoading.postValue(true)
    }

    protected fun hideLoading() {
        _showLoading.postValue(false)
    }

    protected fun showError(error: String?) {
        error?.let {
            _showError.postValue(it)
        }
    }

    protected fun showNoInternetConnection() {
        _showNoInternetConnection.postValue(Unit)
    }

    protected fun showException(exception: Throwable?) {
        exception?.let {
            _showException.postValue(it.message)
        }
    }
}