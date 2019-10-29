package com.example.kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.model.repository.PersonRepos
import com.example.kotlin.view.MainViewState


class PersonViewModel : ViewModel() {

    private val viewState : MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewState.value = MainViewState(PersonRepos.persons)
    }

    fun getViewState() = viewState

    fun updateViewState() {
        viewState.value = MainViewState(PersonRepos.getAnotherPersons())
    }
}