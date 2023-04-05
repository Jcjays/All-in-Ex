package com.adonis.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.adonis.base.databinding.FragmentDashboardBinding
import com.adonis.base.extensions.viewBinding
import com.adonis.base.ui.base.BaseFragment
import com.adonis.base.ui.viewmodels.SampleViewModel

class Dashboard : BaseFragment() {

    override val binding: ViewBinding by viewBinding(FragmentDashboardBinding::inflate)

    private val viewModel : SampleViewModel by activityViewModels()

    override fun observeCommonEvents() {
        observeCommonEvents(viewModel)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {

    }
}