package com.adonis.allinex.arch.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.adonis.allinex.R
import com.adonis.allinex.arch.ui.base.BaseFragment
import com.adonis.allinex.arch.ui.onboarding.OnboardingActivity
import com.adonis.allinex.arch.ui.viewmodels.AuthViewModel
import com.adonis.allinex.databinding.FragmentLoginBinding
import com.adonis.allinex.extensions.showShortToast
import com.adonis.allinex.util.UserPreferences
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var googleOneTapClient : SignInClient

    @Inject
    lateinit var facebookLoginManager : LoginManager

    private val viewModel: AuthViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()

        initObservables()
        initClickable()

    }

    private fun initObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //On-boarding logic
                userPreferences.getIsUserNew.collectLatest { isUserNew ->
                    if (isUserNew) {
                        OnboardingActivity.startActivity(requireContext())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.firebaseSignInWithGoogleChannel.collectLatest { result ->
                    binding.loadingState.root.isVisible = result.isLoading
                    Timber.e("--> ${result.data.toString()}")
                    if (result.data == true) {
                        val action = LoginFragmentDirections.actionDashboardToHomeFragment()
                        findNavController().navigate(action)
                    }

                    checkIfError(result)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.firebaseSignInWithFacebookChannel.collectLatest { result ->
                    binding.loadingState.root.isVisible = result.isLoading

                    if (result.data == true) {
                        val action = LoginFragmentDirections.actionDashboardToHomeFragment()
                        findNavController().navigate(action)
                    }

                    checkIfError(result)
                }
            }
        }
    }

    private fun initClickable() {
        binding.googleLoginButton.setOnClickListener {
            initGoogleSignInRequest()
        }

        binding.facebookLoginButton.setOnClickListener {
            initFacebookLogin()
        }
    }

    private fun initFacebookLogin() {
        facebookLoginManager.logInWithReadPermissions(
            requireActivity(),
            listOf("email", "public_profile")
        )

        facebookLoginManager.registerCallback(
            CallbackManager.Factory.create(),
            facebookCallback
        )
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

        googleOneTapClient.beginSignIn(signInRequest).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intentSender = task.result.pendingIntent.intentSender
                val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                signInLauncher.launch(intentSenderRequest)
            } else {
                Timber.e(task.exception)
                requireActivity().showShortToast("No google accounts detected in the device.")
            }
        }
    }


    /**
     * Callback Handlers | ActivityResultContracts
     */

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                googleOneTapClient.runCatching {
                    getSignInCredentialFromIntent(it.data)
                }.onSuccess { signInCredential ->
                    //if success proceed with firebase authentication.
                    viewModel.firebaseSignInWithGoogle(signInCredential)
                }.onFailure {
                    requireActivity().showShortToast("No google accounts signed-in detected.")
                }
            }
        }

    private val facebookCallback = object : FacebookCallback<LoginResult> {

        override fun onSuccess(result: LoginResult) {
            //if success proceed with firebase authentication.
            viewModel.firebaseSignInWithFacebook(result)
        }

        override fun onCancel() {
            requireActivity().showShortToast("Login with facebook is cancelled.")
        }

        override fun onError(error: FacebookException) {
            requireActivity().showShortToast("Error occurred when logging in with facebook.")
            Timber.e(error.message)
        }

    }
}