package com.example.kotlin.viewmodels

import androidx.annotation.VisibleForTesting
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class MainViewModel(private val personsRepos: PersonsRepos) :  BaseViewModel<List<Person>?>() {

    private val personChannel = personsRepos.getPersons()

    init {
        launch {
            personChannel.consumeEach {
                @Suppress("UNCHECKED_CAST")
                when(it) {
                    is PersonResult.Success<*> -> setData(it.data as? List<Person>)
                    is PersonResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting public
    override fun onCleared() {
        personChannel.cancel()
        super.onCleared()
    }
}