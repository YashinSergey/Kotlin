package com.example.kotlin.model.repository


import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.provider.FireStoreProvider
import com.example.kotlin.model.provider.RemoteDataProvider

object PersonsRepos {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getPersons() = remoteProvider.subscribeToAllPersons()
    fun savePerson(person: Person) = remoteProvider.savePerson(person)
    fun getPersonById(id: String) = remoteProvider.getPersonById(id)
    fun setColor() = remoteProvider.setColor()
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}