package com.adonis.base.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adonis.base.ui.base.BaseActivity
import com.adonis.base.ui.base.BaseFragment
import com.adonis.base.util.SingleLiveEvent

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

    /**
     * Used for activities/fragment do NOT call in view models.
     *
     */
    fun observeCommonEvent(activity: BaseActivity) {
        showLoading.observe(activity){
            activity.showLoading(it)
        }

        showError.observe(activity){
            activity.showError(it)
        }

        showNoInternetConnection.observe(activity){

        }

        showException.observe(activity){

        }
    }

    fun observeCommonEvent(fragment: BaseFragment) {
        (fragment.requireActivity() as BaseActivity).apply {
            showLoading.observe(fragment.viewLifecycleOwner){
                showLoading(it)
            }

            showError.observe(fragment.viewLifecycleOwner){
               showError(it)
            }

            showNoInternetConnection.observe(fragment.viewLifecycleOwner){
                showNoInternetConnection()
            }

            showException.observe(fragment.viewLifecycleOwner){
                showException(it)
            }
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