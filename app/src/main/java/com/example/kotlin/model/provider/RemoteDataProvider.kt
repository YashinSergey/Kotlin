package com.example.kotlin.model.provider

import androidx.lifecycle.LiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person

interface RemoteDataProvider {
    fun subscribeToAllPersons(): LiveData<PersonResult>
    fun getPersonById(id: String): LiveData<PersonResult>
    fun savePerson(person: Person) : LiveData<PersonResult>
    fun setColor(): Person.Color
}