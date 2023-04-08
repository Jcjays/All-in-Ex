package com.adonis.base.arch.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.adonis.base.arch.ui.base.BaseFragment
import com.adonis.base.arch.ui.onboarding.OnboardingActivity
import com.adonis.base.arch.ui.viewmodels.SampleViewModel
import com.adonis.base.databinding.FragmentDashboardBinding
import com.adonis.base.extensions.showShortToast
import com.adonis.base.util.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Dashboard : BaseFragment<FragmentDashboardBinding>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel : SampleViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater) -> FragmentDashboardBinding
        get() = FragmentDashboardBinding::inflate

    override fun initViews(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                userPreferences.getIsUserNew.collectLatest { isUserNew ->
                    if(isUserNew){
                        OnboardingActivity.startActivity(requireContext())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.jokeState.collectLatest {
                    binding.loading.isVisible = it.isLoading
                    binding.textView.text = it.data?.joke

                    it.exception?.let { message ->
                        requireActivity().showShortToast(message)
                    }
                }
            }
        }

        binding.getJokeButton.setOnClickListener {
            viewModel.getJoke()
        }
    }
}