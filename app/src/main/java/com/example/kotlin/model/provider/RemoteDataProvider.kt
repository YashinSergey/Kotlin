package com.example.kotlin.model.provider

import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.entity.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllPersons(): ReceiveChannel<PersonResult>
    suspend fun getPersonById(id: String): Person
    suspend fun savePerson(person: Person) : Person
    suspend fun getCurrentUser(): User?
    suspend fun deletePerson(personId: String)
}