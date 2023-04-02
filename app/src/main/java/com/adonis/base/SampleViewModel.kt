package com.adonis.base

import androidx.lifecycle.viewModelScope
import com.adonis.base.ui.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(): BaseViewModel(){

    fun triggerLoading(boolean : Boolean) = viewModelScope.launch{
        showLoading()
    }

}