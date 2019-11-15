package com.example.kotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.kotlin.model.errors.AuthException
import com.example.kotlin.ui.MainActivity
import com.example.kotlin.viewmodels.BaseViewModel
import com.example.kotlin.ui.viewstates.BaseViewState


abstract class BaseFragment<T, E: BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, E>
    lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
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
        when (t) {
            is AuthException -> activity.startLogin()
            else -> it.message?.let { message ->
                showError(message)
            }
        }
    }

    private fun showError(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}