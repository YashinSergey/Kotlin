package com.example.kotlin.viewmodels

import com.example.kotlin.model.errors.AuthException
import com.example.kotlin.model.repository.PersonsRepos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AuthViewModel(private val personsRepos: PersonsRepos) : BaseViewModel<Boolean?>() {

    fun requestUser(){
        launch {
            personsRepos.getCurrentUser()?.let {
                setData(true)
            } ?: setError(AuthException())
        }
    }
}