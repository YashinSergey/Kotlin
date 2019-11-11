package com.example.kotlin.ui.viewstates

import com.example.kotlin.model.entity.Person


class PersonViewState(person: Person? = null, error: Throwable? = null)
    : BaseViewState<Person?>(person, error)