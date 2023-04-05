package com.adonis.base.arch.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.adonis.base.arch.domain.usecase.GetJokeUseCase
import com.adonis.base.arch.model.remote.Joke
import com.adonis.base.util.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getJokeUseCase: GetJokeUseCase
): BaseViewModel(){

    private val _jokeMutableState = MutableStateFlow(JokeStateWrapper())
    val jokeState = _jokeMutableState.asStateFlow()

   fun getJoke() = viewModelScope.launch{
        getJokeUseCase().collectLatest { response ->
            when(response){
                is ResponseHandler.Loading -> _jokeMutableState.value = JokeStateWrapper(isLoading = true)
                is ResponseHandler.Error -> _jokeMutableState.value = JokeStateWrapper(error = response.message)
                is ResponseHandler.Exception -> _jokeMutableState.value = JokeStateWrapper(exception = response.e)
                is ResponseHandler.Success -> _jokeMutableState.value = JokeStateWrapper(data = response.data)
            }
        }
    }

}

//todo centralized handling of common events.

data class JokeStateWrapper(
    val data : Joke? = null,
    val isLoading : Boolean = false,
    val error : String? = null,
    val exception : Throwable? = null
)