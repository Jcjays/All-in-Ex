package com.adonis.allinex.arch.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonis.allinex.arch.domain.usecase.SignInWithFacebookUseCase
import com.adonis.allinex.arch.domain.usecase.SignInWithGoogleUseCase
import com.adonis.allinex.extensions.identifyFirebaseAuthError
import com.adonis.allinex.util.ResponseHandler
import com.adonis.allinex.util.StateWrapper
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.SignInCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
) : ViewModel() {

    private val _firebaseSignInWithGoogleMutableState = MutableStateFlow(StateWrapper<Boolean>())
    val firebaseSignInWithGoogleState = _firebaseSignInWithGoogleMutableState.asStateFlow()

    private val _firebaseSignInWithFacebookMutableState = MutableStateFlow(StateWrapper<Boolean>())
    val firebaseSignInWithFacebookState = _firebaseSignInWithFacebookMutableState.asStateFlow()

    fun firebaseSignInWithGoogle(signInCredential: SignInCredential) =
        viewModelScope.launch(Dispatchers.IO) {
            signInWithGoogleUseCase(signInCredential).collectLatest { response ->
                when (response) {
                    is ResponseHandler.Loading -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(isLoading = true)
                    is ResponseHandler.Success -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(data = response.data)
                    is ResponseHandler.Error -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(error = response.message)
                    is ResponseHandler.Exception -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(exception = response.e.identifyFirebaseAuthError())
                }
            }
        }

    fun firebaseSignInWithFacebook(loginResult: LoginResult) =
        viewModelScope.launch(Dispatchers.IO) {
            signInWithFacebookUseCase(loginResult).collectLatest { response ->
                when(response){
                    is ResponseHandler.Loading -> _firebaseSignInWithFacebookMutableState.value =
                        StateWrapper(isLoading = true)
                    is ResponseHandler.Success -> _firebaseSignInWithFacebookMutableState.value =
                        StateWrapper(data = response.data)
                    is ResponseHandler.Error -> _firebaseSignInWithFacebookMutableState.value =
                        StateWrapper(error = response.message)
                    is ResponseHandler.Exception -> _firebaseSignInWithFacebookMutableState.value =
                        StateWrapper(exception = response.e.identifyFirebaseAuthError())
                }
            }
        }

}

