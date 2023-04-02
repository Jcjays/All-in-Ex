package com.adonis.base.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adonis.base.ui.viewmodels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var baseViewModel : BaseViewModel

    protected abstract fun getLayoutResourceId() : Int

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        Timber.e("Mwa")
        initViews()
        observeCommonEvent()
    }


    private fun observeCommonEvent() {
        baseViewModel.showLoading.observe(this){
            Timber.e("YAWA")
            Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
        }

        baseViewModel.showNoInternetConnection.observe(this){

        }

        baseViewModel.showError.observe(this){

        }

        baseViewModel.showException.observe(this){

        }
    }

    protected fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showNoInternetConnection() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }

    protected fun showException(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }
}