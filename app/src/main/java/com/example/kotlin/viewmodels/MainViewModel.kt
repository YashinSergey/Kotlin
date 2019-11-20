package com.example.kotlin.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.MainViewState


class MainViewModel(private val personsRepos: PersonsRepos) :  BaseViewModel<List<Person>?, MainViewState>() {

    @Suppress("UNCHECKED_CAST")
    private val personObserver = Observer<PersonResult> {
        it ?: return@Observer

        when(it) {
            is PersonResult.Success<*> -> viewStateLiveData.value = MainViewState(persons = it.data as? List<Person>)
            is PersonResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
        }
    }

    init {
        viewStateLiveData.value = MainViewState()
        personsRepos.getPersons().observeForever(personObserver)
    }

    override fun getViewState() = viewStateLiveData

    @VisibleForTesting public
    override fun onCleared() {
        personsRepos.getPersons().removeObserver(personObserver)
        super.onCleared()
    }
}