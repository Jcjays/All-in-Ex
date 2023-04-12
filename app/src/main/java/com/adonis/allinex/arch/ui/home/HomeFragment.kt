package com.adonis.allinex.arch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.adonis.allinex.arch.ui.base.BaseFragment
import com.adonis.allinex.arch.ui.viewmodels.AuthViewModel
import com.adonis.allinex.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: AuthViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun initViews(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCurrentUserState.collectLatest {
                    binding.textView.text = it.data?.email
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signOutChannel.collectLatest { result ->
                    binding.loadingState.root.isVisible = result.isLoading

                    if (result.data == true) {
                        val destination = HomeFragmentDirections.actionHomeFragmentToDashboard()
                        findNavController().navigate(destination)
                    }
                }
            }
        }

        viewModel.getCurrentUser()


        binding.button2.setOnClickListener {
            viewModel.signOut()
        }
    }
}