package com.adonis.base

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
        viewModel.triggerLoading(true)
    }


}