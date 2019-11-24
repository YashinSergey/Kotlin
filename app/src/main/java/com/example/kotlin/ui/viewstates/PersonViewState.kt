package com.example.kotlin.ui.viewstates

import com.example.kotlin.model.entity.Person


class PersonViewState(data: Data = Data(), error: Throwable? = null)
    : BaseViewState<PersonViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val person: Person? = null)
}