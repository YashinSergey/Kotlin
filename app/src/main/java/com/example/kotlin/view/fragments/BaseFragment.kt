package com.example.kotlin.view.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.kotlin.viewmodels.BaseViewModel
import com.example.kotlin.view.viewstates.BaseViewState

abstract class BaseFragment<T, E: BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, E>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { it }
        viewModel.getViewState().observe(this, Observer<E> {
            it ?: return@Observer
            it.error?.let { error ->
                renderError(error)
                return@Observer
            }
            renderData(it.data)
        })
    }

    abstract fun renderData(data: T)
    
    private fun renderError(t: Throwable?) = t?.let {
        it.message?.let { message ->
            showError(message)
        }
    }

    private fun showError(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}