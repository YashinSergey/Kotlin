package com.example.kotlin.view.viewstates

import com.example.kotlin.model.entity.Person

class MainViewState(val persons: List<Person>? = null, error: Throwable? = null) : BaseViewState<List<Person>?>(persons, error)