package com.example.kotlin.model.repository


import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.provider.RemoteDataProvider

class PersonsRepos(private val remoteProvider: RemoteDataProvider) {

    fun getPersons() = remoteProvider.subscribeToAllPersons()
    fun savePerson(person: Person) = remoteProvider.savePerson(person)
    fun getPersonById(id: String) = remoteProvider.getPersonById(id)
    fun setColor() = remoteProvider.setColor()
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}