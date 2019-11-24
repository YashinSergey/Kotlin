package com.example.kotlin.viewmodels

import androidx.annotation.VisibleForTesting
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.ui.viewstates.PersonData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PersonViewModel(private val personsRepos: PersonsRepos) : BaseViewModel<PersonData>() {

    private val currentPerson: Person?
        get() = getViewState().poll()?.person

    fun save(person: Person) {
        setData(PersonData(person = person))
    }

    @VisibleForTesting public
    override fun onCleared() {
        launch {
            currentPerson?.let {
                personsRepos.savePerson(it)
            }
            super.onCleared()
        }
    }

    fun loadPerson(personId: String) {
        launch {
            try {
                personsRepos.getPersonById(personId).let {
                    setData(PersonData(person = it))
                }
            } catch (error: Throwable){
                setError(error)
            }
        }
    }

    fun deletePerson() {
        launch {
            try {
                currentPerson?.let {
                    personsRepos.deletePerson(it.id)
                    setData(PersonData(isDeleted = true))
                }
            } catch (error: Throwable){
                setError(error)
            }
        }
    }
}