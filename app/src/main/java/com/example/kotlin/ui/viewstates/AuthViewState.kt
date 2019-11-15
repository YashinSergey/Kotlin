package com.example.kotlin.ui.viewstates

class AuthViewState(authenticated: Boolean? = null, error: Throwable? = null): BaseViewState<Boolean?>(authenticated, error)