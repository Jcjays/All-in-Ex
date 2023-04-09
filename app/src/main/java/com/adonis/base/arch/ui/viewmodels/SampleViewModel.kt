package com.adonis.base.arch.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonis.base.arch.domain.usecase.GetJokeUseCase
import com.adonis.base.arch.domain.usecase.SignInWithGoogleUseCase
import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.extensions.identifyRetrofitError
import com.adonis.base.util.ResponseHandler
import com.google.android.gms.auth.api.identity.SignInCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getJokeUseCase: GetJokeUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ViewModel() {

    private val _jokeMutableState = MutableStateFlow(StateWrapper<Joke>())
    val jokeState = _jokeMutableState.asStateFlow()

    private val _firebaseSignInWithGoogleMutableState = MutableStateFlow(StateWrapper<Boolean>())
    val firebaseSignInWithGoogleState =_firebaseSignInWithGoogleMutableState.asStateFlow()

    fun getJoke() = viewModelScope.launch(Dispatchers.IO) {
        getJokeUseCase().collectLatest { response ->
            when (response) {
                is ResponseHandler.Loading -> _jokeMutableState.value =
                    StateWrapper(isLoading = true)
                is ResponseHandler.Success -> _jokeMutableState.value =
                    StateWrapper(data = response.data)
                is ResponseHandler.Error -> _jokeMutableState.value =
                    StateWrapper(error = response.message)
                is ResponseHandler.Exception -> _jokeMutableState.value =
                    StateWrapper(exception = response.e.identifyRetrofitError())
            }
        }
    }

    fun firebaseSignInWithGoogle(signInCredential: SignInCredential) =
        viewModelScope.launch(Dispatchers.IO) {
            signInWithGoogleUseCase(signInCredential).collectLatest { response ->
                when(response){
                    is ResponseHandler.Loading -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(isLoading = true)
                    is ResponseHandler.Success -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(data = response.data)
                    is ResponseHandler.Error -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(error = response.message)
                    is ResponseHandler.Exception -> _firebaseSignInWithGoogleMutableState.value =
                        StateWrapper(exception = response.e.message)
                }
            }
        }
}

data class StateWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val exception: String? = null,
)