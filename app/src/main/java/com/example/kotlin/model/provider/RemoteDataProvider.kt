package com.example.kotlin.model.provider

import androidx.lifecycle.LiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.entity.User

interface RemoteDataProvider {
    fun subscribeToAllPersons(): LiveData<PersonResult>
    fun getPersonById(id: String): LiveData<PersonResult>
    fun savePerson(person: Person) : LiveData<PersonResult>
    fun getCurrentUser(): LiveData<User?>
    fun deletePerson(personId: String) :LiveData<PersonResult>
}