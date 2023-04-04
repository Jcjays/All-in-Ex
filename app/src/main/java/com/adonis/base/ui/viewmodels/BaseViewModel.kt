package com.adonis.base.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adonis.base.ui.base.BaseActivity
import com.adonis.base.util.SingleLiveEvent
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val _showLoading = SingleLiveEvent<Boolean>()
    private val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _showError = SingleLiveEvent<String>()
    private val showError: LiveData<String>
        get() = _showError

    private val _showNoInternetConnection = SingleLiveEvent<Unit>()
    private val showNoInternetConnection: LiveData<Unit>
        get() = _showNoInternetConnection

    private val _showException = SingleLiveEvent<String>()
    private val showException: LiveData<String>
        get() = _showException

    open fun observeCommonEvent(activity: BaseActivity) {
        showLoading.observe(activity){
            Timber.e("Loading")
        }

        showError.observe(activity){

        }

        showNoInternetConnection.observe(activity){

        }

        showException.observe(activity){

        }
    }

    protected fun showLoading() {
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