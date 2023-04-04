package com.adonis.base.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adonis.base.ui.viewmodels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var baseViewModel : BaseViewModel

    protected abstract fun getLayoutResourceId() : Int

    protected abstract fun observeCommonEvent()

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        initViews()
        observeCommonEvent()
    }

    open fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    open fun showNoInternetConnection() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }

    open fun showException(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }
}