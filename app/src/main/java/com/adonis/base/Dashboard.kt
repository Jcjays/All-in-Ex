package com.adonis.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.adonis.base.arch.ui.base.BaseFragment
import com.adonis.base.arch.ui.viewmodels.SampleViewModel
import com.adonis.base.databinding.FragmentDashboardBinding
import com.adonis.base.extensions.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Dashboard : BaseFragment() {

    override val binding by viewBinding(FragmentDashboardBinding::inflate)

    private val viewModel : SampleViewModel by activityViewModels()

    override fun observeCommonEvents() {
        observeCommonEvents(viewModel)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.jokeState.collectLatest {
                    binding.textView.text = it.data?.joke
                }
            }
        }

        viewModel.getJoke()
    }
}