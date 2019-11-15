package com.example.kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.ui.viewstates.BaseViewState

open class BaseViewModel<T, E : BaseViewState<T>>: ViewModel() {

    open val viewStateLiveData = MutableLiveData<E>()
    open fun getViewState(): LiveData<E> = viewStateLiveData
}