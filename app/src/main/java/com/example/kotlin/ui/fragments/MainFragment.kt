package com.example.kotlin.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import com.example.kotlin.ui.adapters.MainAdapter
import com.example.kotlin.ui.viewstates.MainViewState
import com.example.kotlin.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment: BaseFragment<List<Person>?, MainViewState>() {

    private lateinit var adapter: MainAdapter

    override val viewModel: MainViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.setTheme(R.style.AppTheme)
        adapter = MainAdapter{
            activity.run { replaceFragment(PersonFragment.newInstance(it.id)) }
        }

        initViews(adapter)

        floatingActionButton.setOnClickListener {
            activity.run { replaceFragment(PersonFragment.newInstance(null)) }
        }
    }

    private fun initViews(a: MainAdapter) {
        personList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = a
        }
    }

    override fun renderData(data: List<Person>?) {
        data?.let { adapter.refreshPersonsList(it) }
    }
}