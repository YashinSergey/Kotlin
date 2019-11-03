package com.example.kotlin.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import com.example.kotlin.view.MainActivity
import com.example.kotlin.view.adapters.MainAdapter
import com.example.kotlin.view.viewstates.MainViewState
import com.example.kotlin.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment: BaseFragment<List<Person>?, MainViewState>() {

    private lateinit var adapter: MainAdapter
    private lateinit var activity: MainActivity
    private lateinit var floatingActionButton: FloatingActionButton

    override val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java)}

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        activity = getActivity() as MainActivity

        adapter = MainAdapter{
            activity.run { replaceFragment(PersonFragment.newInstance(it.id)) }
        }

        initViews(adapter, view)

        floatingActionButton.setOnClickListener {
            activity.run { replaceFragment(PersonFragment.newInstance(null)) }
        }
        return view
    }

    private fun initViews(a: MainAdapter, view: View) {
        val personList = view.findViewById<RecyclerView>(R.id.personList)
        personList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = a
        }
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
    }

    override fun renderData(data: List<Person>?) {
        data?.let { adapter.refreshPersonsList(it) }
    }
}