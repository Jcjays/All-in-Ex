package com.adonis.base.arch.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.adonis.base.R
import com.adonis.base.arch.ui.base.BaseFragment
import com.adonis.base.arch.ui.onboarding.OnboardingActivity
import com.adonis.base.arch.ui.viewmodels.SampleViewModel
import com.adonis.base.databinding.FragmentDashboardBinding
import com.adonis.base.extensions.showShortToast
import com.adonis.base.util.UserPreferences
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class Dashboard : BaseFragment<FragmentDashboardBinding>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel: SampleViewModel by activityViewModels()

    private val oneTapClient by lazy { Identity.getSignInClient(requireActivity()) }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                oneTapClient.runCatching {
                    getSignInCredentialFromIntent(it.data)
                }.onSuccess { signInCredential ->
                    val token = signInCredential.googleIdToken
                    val googleCredential = getCredential(token, null)
                    viewModel.firebaseSignInWithGoogle(googleCredential)
                }.onFailure {
                    requireActivity().showShortToast("No google account signed-in detected.")
                }
            }
        }

    override val bindingInflater: (LayoutInflater) -> FragmentDashboardBinding
        get() = FragmentDashboardBinding::inflate

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                userPreferences.getIsUserNew.collectLatest { isUserNew ->
                    if (isUserNew) {
                        OnboardingActivity.startActivity(requireContext())
                    }
                }
            }
        }

        binding.googleLoginButton.setOnClickListener {
            initGoogleSignInRequest()
        }
    }

    private fun initGoogleSignInRequest() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.firebase_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()

        oneTapClient.beginSignIn(signInRequest).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intentSender = task.result.pendingIntent.intentSender
                val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                signInLauncher.launch(intentSenderRequest)
            }else{
                Timber.e(task.exception)
                requireActivity().showShortToast("No google accounts detected in the device.")
            }
        }
    }
}