package com.adonis.base

import com.adonis.base.ui.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(): BaseViewModel(){

    fun triggerLoading(boolean : Boolean){
        showLoading()
    }

}