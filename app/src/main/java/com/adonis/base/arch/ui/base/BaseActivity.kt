package com.adonis.base.arch.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adonis.base.extensions.showShortToast
import com.adonis.base.arch.ui.viewmodels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var baseViewModel : BaseViewModel

    protected abstract fun getLayoutResourceId() : Int

    protected abstract fun observeCommonEvents()

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        initViews()
        observeCommonEvents()
    }

    fun observeCommonEvents(viewModel: BaseViewModel){
        viewModel.observeCommonEvent(this)
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