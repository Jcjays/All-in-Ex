package com.adonis.allinex.arch.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonis.allinex.arch.domain.usecase.GetCurrentUserUseCase
import com.adonis.allinex.arch.domain.usecase.SignInWithFacebookUseCase
import com.adonis.allinex.arch.domain.usecase.SignInWithGoogleUseCase
import com.adonis.allinex.arch.domain.usecase.SignOutUseCase
import com.adonis.allinex.extensions.identifyFirebaseAuthError
import com.adonis.allinex.util.ResponseHandler
import com.adonis.allinex.util.StateWrapper
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {


    private val _firebaseSignInWithGoogleMutableChannel = Channel<StateWrapper<Boolean>>()
    val firebaseSignInWithGoogleChannel = _firebaseSignInWithGoogleMutableChannel.receiveAsFlow()

    private val _firebaseSignInWithFacebookMutableChannel = Channel<StateWrapper<Boolean>>()
    val firebaseSignInWithFacebookChannel = _firebaseSignInWithFacebookMutableChannel.receiveAsFlow()

    private val _signOutMutableChannel = Channel<StateWrapper<Boolean>>()
    val signOutChannel = _signOutMutableChannel.receiveAsFlow()

    private val _getCurrentUserMutableState = MutableStateFlow(StateWrapper<FirebaseUser>())
    val getCurrentUserState = _getCurrentUserMutableState.asStateFlow()

    fun firebaseSignInWithGoogle(signInCredential: SignInCredential) = viewModelScope.launch {
        signInWithGoogleUseCase(signInCredential).collectLatest { response ->
            when (response) {
                is ResponseHandler.Loading -> _firebaseSignInWithGoogleMutableChannel.send(
                    StateWrapper(isLoading = true)
                )
                is ResponseHandler.Success -> _firebaseSignInWithGoogleMutableChannel.send(
                    StateWrapper(data = response.data)
                )
                is ResponseHandler.Error -> _firebaseSignInWithGoogleMutableChannel.send(
                    StateWrapper(error = response.message)
                )
                is ResponseHandler.Exception -> _firebaseSignInWithGoogleMutableChannel.send(
                    StateWrapper(exception = response.e.identifyFirebaseAuthError())
                )
            }
        }
    }

    fun firebaseSignInWithFacebook(loginResult: LoginResult) = viewModelScope.launch {
        signInWithFacebookUseCase(loginResult).collectLatest { response ->
            when (response) {
                is ResponseHandler.Loading -> _firebaseSignInWithFacebookMutableChannel.send(
                    StateWrapper(isLoading = true)
                )
                is ResponseHandler.Success -> _firebaseSignInWithFacebookMutableChannel.send(
                    StateWrapper(data = response.data)
                )
                is ResponseHandler.Error -> _firebaseSignInWithFacebookMutableChannel.send(
                    StateWrapper(error = response.message)
                )
                is ResponseHandler.Exception -> _firebaseSignInWithFacebookMutableChannel.send(
                    StateWrapper(exception = response.e.identifyFirebaseAuthError())
                )
            }
        }
    }

    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {
        getCurrentUserUseCase().collectLatest { response ->
            when (response) {
                is ResponseHandler.Loading -> _getCurrentUserMutableState.value =
                    StateWrapper(isLoading = true)
                is ResponseHandler.Error -> _getCurrentUserMutableState.value =
                    StateWrapper(error = response.message)
                is ResponseHandler.Success -> _getCurrentUserMutableState.value =
                    StateWrapper(data = response.data)
                is ResponseHandler.Exception -> _getCurrentUserMutableState.value =
                    StateWrapper(exception = response.e.identifyFirebaseAuthError())
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        signOutUseCase().collectLatest { response ->
            when(response){
                is ResponseHandler.Loading -> _signOutMutableChannel.send(
                    StateWrapper(isLoading = true)
                )
                is ResponseHandler.Success -> _signOutMutableChannel.send(
                    StateWrapper(data = response.data)
                )
                is ResponseHandler.Error -> _signOutMutableChannel.send(
                    StateWrapper(error = response.message)
                )
                is ResponseHandler.Exception -> _signOutMutableChannel.send(
                    StateWrapper(exception = response.e.identifyFirebaseAuthError())
                )
            }
        }
    }

}

