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
import com.adonis.base.arch.ui.base.BaseFragment
import com.adonis.base.arch.ui.onboarding.OnboardingActivity
import com.adonis.base.arch.ui.viewmodels.SampleViewModel
import com.adonis.base.databinding.FragmentDashboardBinding
import com.adonis.base.extensions.showShortToast
import com.adonis.base.util.UserPreferences
import com.google.android.gms.auth.api.identity.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class Dashboard : BaseFragment<FragmentDashboardBinding>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val viewModel: SampleViewModel by activityViewModels()

    private val oneTapClient by lazy { Identity.getSignInClient(requireActivity()) }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                oneTapClient.runCatching {
                    getSignInCredentialFromIntent(it.data)
                }.onSuccess { signInCredential ->
                    val token = signInCredential.googleIdToken

                    if (token != null) {
                        Timber.e(token)
                    }

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

        }
    }

    private fun initGoogleSignInRequest() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("457234146619-f40fff56pc5ejd7fqpjibidm0sjsgu0g.apps.googleusercontent.com")
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
                requireActivity().showShortToast("Unknown error occurred.")
            }
        }
    }
}