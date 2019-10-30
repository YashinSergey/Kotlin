package com.example.kotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.R
import com.example.kotlin.view.adapter.MainAdapter
import com.example.kotlin.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val personViewModel by lazy {ViewModelProviders.of(this).get(MainViewModel::class.java)}
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter{

        }
        initPersonList(adapter)

        createObserver()

        floatingActionButton.setOnClickListener {

        }
    }

    private fun createObserver() {
        personViewModel.getViewState().observe(this, Observer {
            it?.let {
                adapter.refreshPersonsList(it)
            }
        })
    }

    private fun initPersonList(a: MainAdapter) {
        personList.layoutManager = LinearLayoutManager(this)
        personList.adapter = a
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }
}
