package com.example.kotlin.viewmodels

import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.PersonViewState

class PersonViewModel(private val personsRepos: PersonsRepos) : BaseViewModel<Person?, PersonViewState>() {

    private var pendingPerson: Person? = null

    init {
        viewStateLiveData.value = PersonViewState()
    }

    fun save(person: Person) {
        pendingPerson = person
    }

    override fun onCleared() {
        pendingPerson?.let {
            personsRepos.savePerson(it)
        }
    }

    fun loadPerson(personId: String) {
        personsRepos.getPersonById(personId).observeForever {
            it ?: return@observeForever
            when(it) {
                is PersonResult.Success<*> -> viewStateLiveData.value = PersonViewState(person = it.data as? Person)
                is PersonResult.Error -> viewStateLiveData.value = PersonViewState(error = it.error)
            }
        }
    }


}