package com.example.kotlin.model.repository

import com.example.kotlin.model.entity.Person


object PersonRepos {

    val persons = listOf(
        Person("Marilyn Monroe:","American actress, singer, model", 0xfff06292 .toInt()),
        Person("Abraham Lincoln:","US President during American civil war", 0xff9575cd .toInt()),
        Person("Mother Teresa:","Macedonian Catholic missionary nun", 0xff64b5f6 .toInt()),
        Person("John F. Kennedy:", "US President 1961 - 1963", 0xff4db6ac .toInt()))

//    fun getPersons() {}

    fun getAnotherPersons() = listOf(
        Person("Martin Luther King:","American civil rights campaigner", 0xffb2ff59 .toInt()),
        Person("Nelson Mandela:","South African President anti-apartheid campaigner",0xffffeb3b .toInt()),
        Person("Queen Elizabeth:","British monarch since 1954", 0xffff6e40 .toInt()),
        Person("Winston Churchill:","British Prime Minister during WWII", 0xfff06292 .toInt()),
        Person("Donald Trump:", "US President, Businessman", 0xff9575cd .toInt()),
        Person("Bill Gates:","American businessman, founder of Microsoft", 0xff64b5f6 .toInt()),
        Person("Muhammad Ali:","American boxer and civil rights campaigner", 0xff4db6ac .toInt()),
        Person("Mahatma Gandhi:","Leader of Indian independence movement", 0xffb2ff59 .toInt()),
        Person("Margaret Thatcher:","British Prime Minister 1979 - 1990", 0xffffeb3b .toInt()),
        Person("Christopher Columbus:", "Italian explorer", 0xffff6e40 .toInt()),
        Person("Charles Darwin:","British scientist, author of the theory of evolution", 0xfff06292 .toInt()),
        Person("Elvis Presley:","American musician, the king of Rock and Roll or simply \"the King\"", 0xff9575cd .toInt()),
        Person("Albert Einstein:","German scientist, author of the theory of relativity", 0xff64b5f6 .toInt()),
        Person("Paul McCartney:","British musician, member of Beatles", 0xff4db6ac .toInt())
    )
}