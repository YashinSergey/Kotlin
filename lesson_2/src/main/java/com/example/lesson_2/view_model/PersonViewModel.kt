package com.example.lesson_2.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson_2.model.repository.PersonRepos
import com.example.lesson_2.view.MainViewState

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