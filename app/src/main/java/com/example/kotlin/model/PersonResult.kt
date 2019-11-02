package com.example.kotlin.model

sealed class PersonResult {

    data class Success<out T> (val data: T) : PersonResult()
    data class Error (val error: Throwable) : PersonResult()
}