package com.example.kotlin.viewmodels

import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.PersonViewState

class PersonViewModel(private val personsRepos: PersonsRepos) : BaseViewModel<PersonViewState.Data, PersonViewState>() {

    private val pendingPerson: Person?
        get() = viewStateLiveData.value?.data?.person

    fun save(person: Person) {
        viewStateLiveData.value = PersonViewState(PersonViewState.Data(person = person))
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
                is PersonResult.Success<*> -> viewStateLiveData.value = PersonViewState(PersonViewState.Data(person = it.data as? Person))
                is PersonResult.Error -> viewStateLiveData.value = PersonViewState(error = it.error)
            }
        }
    }

    fun deletePerson() {
        pendingPerson?.let {
            personsRepos.deletePerson(it.id).observeForever {res ->
                res?.let { result ->
                    when(result) {
                        is PersonResult.Success<*> -> viewStateLiveData.value = PersonViewState(PersonViewState.Data(isDeleted = true))
                        is PersonResult.Error -> viewStateLiveData.value = PersonViewState(error = result.error)
                    }
                }
            }
        }
    }
}