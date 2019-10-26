package com.example.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModule : ViewModel() {

    private val viewStateLiveData : MutableLiveData<String> = MutableLiveData()

    init {
        viewStateLiveData.value = "Hello, Kotlin"
    }

    fun viewState() : LiveData<String> = viewStateLiveData


}