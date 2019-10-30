package com.example.kotlin.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.view.MainActivity
import com.example.kotlin.view.adapter.MainAdapter
import com.example.kotlin.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment: Fragment() {



    private val personViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java)}
    private lateinit var adapter: MainAdapter
    private lateinit var activity: MainActivity
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var personFragment: PersonFragment

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        activity = getActivity() as MainActivity
        personFragment = PersonFragment()

        adapter = MainAdapter{
            PersonFragment.start(activity, it)
        }

        initViews(adapter, view)

        createObserver()

        floatingActionButton.setOnClickListener {
            activity.replaceFragment(personFragment)
        }
        return view
    }

    private fun createObserver() {
        personViewModel.getViewState().observe(this, Observer {
            it?.let {
                adapter.refreshPersonsList(it)
            }
        })
    }

    private fun initViews(a: MainAdapter, view: View) {
        val personList = view.findViewById<RecyclerView>(R.id.personList)
        personList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = a
        }
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
    }
}