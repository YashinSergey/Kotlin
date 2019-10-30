package com.example.kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonRepos

class PersonViewModel : ViewModel() {

    private var pendingPerson: Person? = null

    fun save(person: Person) {
        pendingPerson = person
    }

    override fun onCleared() {
        pendingPerson?.let {
            PersonRepos.savePerson(it)
        }
    }
}