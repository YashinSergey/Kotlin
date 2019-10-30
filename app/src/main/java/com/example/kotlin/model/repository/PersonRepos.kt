package com.example.kotlin.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.entity.Person
import java.util.*


object PersonRepos {

    private var whiteColor: Boolean = true
    private val personsLiveData = MutableLiveData<List<Person>>()

    private val persons = mutableListOf(
        Person(UUID.randomUUID().toString(),"Marilyn Monroe:","American actress, singer, model",setColor()),
        Person(UUID.randomUUID().toString(),"Abraham Lincoln:","US President during American civil war", setColor()),
        Person(UUID.randomUUID().toString(),"Mother Teresa:","Macedonian Catholic missionary nun", setColor()),
        Person(UUID.randomUUID().toString(),"John F. Kennedy:", "US President 1961 - 1963", setColor()),
        Person(UUID.randomUUID().toString(),"Martin Luther King:","American civil rights campaigner", setColor()),
        Person(UUID.randomUUID().toString(),"Nelson Mandela:","South African President anti-apartheid campaigner",setColor()),
        Person(UUID.randomUUID().toString(),"Queen Elizabeth:","British monarch since 1954", setColor()),
        Person(UUID.randomUUID().toString(),"Winston Churchill:","British Prime Minister during WWII",setColor()),
        Person(UUID.randomUUID().toString(),"Donald Trump:", "US President, Businessman",setColor()),
        Person(UUID.randomUUID().toString(),"Bill Gates:","American businessman, founder of Microsoft", setColor()),
        Person(UUID.randomUUID().toString(),"Muhammad Ali:","American boxer and civil rights campaigner", setColor()),
        Person(UUID.randomUUID().toString(),"Mahatma Gandhi:","Leader of Indian independence movement", setColor()),
        Person(UUID.randomUUID().toString(),"Margaret Thatcher:","British Prime Minister 1979 - 1990", setColor()),
        Person(UUID.randomUUID().toString(),"Christopher Columbus:", "Italian explorer", setColor()),
        Person(UUID.randomUUID().toString(),"Charles Darwin:","British scientist, author of the theory of evolution", setColor()),
        Person(UUID.randomUUID().toString(),"Elvis Presley:","American musician, the king of Rock and Roll or simply \"the King\"", setColor()),
        Person(UUID.randomUUID().toString(),"Albert Einstein:","German scientist, author of the theory of relativity", setColor()),
        Person(UUID.randomUUID().toString(),"Paul McCartney:","British musician, member of Beatles", setColor())
    )

    fun getPersons() : LiveData<List<Person>> {
        return personsLiveData
    }

    fun savePerson(person: Person) {
        addOrReplace(person)
        personsLiveData.value = persons
    }

    private fun addOrReplace(person: Person) {
        for (i in 0 until persons.size) {
            if (persons[i] == person) {
                persons[i] = person
                return
            }
        }
        persons.add(person)
    }

    private fun setColor(): Person.Color {
        var color = Person.Color.WHITE
        if (whiteColor) {
            whiteColor = false
        } else {
            color =  Person.Color.DARK_WHITE
            whiteColor = true
        }
        return color
    }
}