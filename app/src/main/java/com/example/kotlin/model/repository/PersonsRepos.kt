package com.example.kotlin.model.repository

import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.provider.RemoteDataProvider

class PersonsRepos(private val remoteProvider: RemoteDataProvider) {

    fun getPersons() = remoteProvider.subscribeToAllPersons()
    suspend fun savePerson(person: Person) = remoteProvider.savePerson(person)
    suspend fun getPersonById(id: String) = remoteProvider.getPersonById(id)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun deletePerson(personId: String) = remoteProvider.deletePerson(personId)
}