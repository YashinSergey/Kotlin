package com.example.kotlin.viewmodels

import com.example.kotlin.model.errors.AuthException
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.AuthViewState

class AuthViewModel(private val personsRepos: PersonsRepos) : BaseViewModel<Boolean?, AuthViewState>() {

    fun requestUser(){
        personsRepos.getCurrentUser().observeForever {
            viewStateLiveData.value = if(it != null) {
                AuthViewState(authenticated = true)
            } else {
                AuthViewState(error = AuthException())
            }
        }
    }
}