package com.example.kotlin.viewmodels

import androidx.lifecycle.Observer
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.MainViewState

class MainViewModel :  BaseViewModel<List<Person>?, MainViewState>() {

    private val personObserver = Observer<PersonResult> {
        it ?: return@Observer

        when(it) {
            is PersonResult.Success<*> -> viewStateLiveData.value = MainViewState(persons = it.data as? List<Person>)
            is PersonResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
        }
    }

    private val personsRepos = PersonsRepos.getPersons()

    init {
        viewStateLiveData.value = MainViewState()
        personsRepos.observeForever(personObserver)
    }

    override fun getViewState() = viewStateLiveData

    override fun onCleared() {
        personsRepos.removeObserver(personObserver)
        super.onCleared()
    }
}