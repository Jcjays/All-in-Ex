package com.adonis.base.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.adonis.base.ui.viewmodels.BaseViewModel

abstract class BaseFragment : Fragment() {

    protected abstract val binding : ViewBinding

    protected abstract fun observeCommonEvents()

    protected abstract fun initViews(view: View, savedInstanceState: Bundle?)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCommonEvents()
        initViews(view, savedInstanceState)
    }

    fun observeCommonEvents(viewModel : BaseViewModel){
        viewModel.observeCommonEvent(this)
    }
}