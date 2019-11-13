package com.example.kotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.R
import com.example.kotlin.ui.viewstates.AuthViewState
import com.example.kotlin.viewmodels.AuthViewModel

class AuthFragment: BaseFragment<Boolean?, AuthViewState>() {

    override val viewModel by lazy { ViewModelProviders.of(activity).get(AuthViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.setTheme(R.style.AuthTheme)
        return super.onCreateView(inflater, container, savedInstanceState)
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
