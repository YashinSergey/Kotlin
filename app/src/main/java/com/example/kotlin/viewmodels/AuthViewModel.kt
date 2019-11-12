package com.example.kotlin.viewmodels

import com.example.kotlin.model.errors.AuthException
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.AuthViewState

class AuthViewModel : BaseViewModel<Boolean?, AuthViewState>() {

    fun requestUser(){
        PersonsRepos.getCurrentUser().observeForever {
            viewStateLiveData.value = when {
                it != null -> AuthViewState(authenticated = true)
                else -> AuthViewState(error = AuthException())
            }
        }
    }
}