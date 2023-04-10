package com.adonis.allinex.arch.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adonis.allinex.extensions.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getLayoutResourceId() : Int

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        initViews()
    }

    fun showLoading(isLoading: Boolean){
        showShortToast("Loading")
    }

    fun showError(message: String) {
        showShortToast(message)
    }

    fun showNoInternetConnection() {
        showShortToast("No internet connection")
    }

    fun showException(message: String) {
        showShortToast(message)
    }
}