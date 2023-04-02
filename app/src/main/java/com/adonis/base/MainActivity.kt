package com.adonis.base

import android.widget.Toast
import androidx.activity.viewModels
import com.adonis.base.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: SampleViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun initViews() {
        Timber.e("Im here")
        viewModel.observeCommonEvent(this)
        viewModel.triggerLoading(true)
    }


}