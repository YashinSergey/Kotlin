package com.example.kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.model.repository.PersonRepos
import com.example.kotlin.view.MainViewState


class MainViewModel : ViewModel() {

    private val viewState : MutableLiveData<MainViewState> = MutableLiveData()

    init {
        PersonRepos.getPersons().observeForever{ persons ->
          persons?.let { viewState.value = viewState.value?.copy(persons = it) ?: MainViewState(it) }
        }
    }

    fun getViewState() = viewState
}