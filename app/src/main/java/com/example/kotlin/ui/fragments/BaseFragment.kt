package com.example.kotlin.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlin.model.errors.AuthException
import com.example.kotlin.ui.MainActivity
import com.example.kotlin.viewmodels.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
abstract class BaseFragment<E> : Fragment(), CoroutineScope {

    abstract val viewModel: BaseViewModel<E>
    lateinit var activity: MainActivity

    override val coroutineContext: CoroutineContext by lazy { Dispatchers.Main + Job() }
    lateinit var dataJob: Job
    lateinit var errorJob: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    abstract fun renderData(data: E)
    
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