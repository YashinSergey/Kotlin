package com.example.kotlin.ui.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.R
import com.example.kotlin.ui.viewstates.AuthViewState
import com.example.kotlin.viewmodels.AuthViewModel

class AuthFragment: BaseFragment<Boolean?, AuthViewState>() {

    override val viewModel by lazy { ViewModelProviders.of(activity).get(AuthViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.setTheme(R.style.AuthTheme)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            activity.replaceFragment(activity.mainFragment)
        }
    }
}
