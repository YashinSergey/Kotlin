package com.example.lesson_2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson_2.R
import com.example.lesson_2.model.adapter.PersonAdapter
import com.example.lesson_2.view_model.PersonViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val personViewModel by lazy {ViewModelProviders.of(this).get(PersonViewModel::class.java)}
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PersonAdapter()
        initPersonList(adapter)

        createObserver()
    }

    private fun createObserver() {
        personViewModel.getViewState().observe(this, Observer {
            it?.let {
                adapter.refreshPersonList(it)
            }
        })
    }

    private fun initPersonList(a: PersonAdapter) {
        personList.layoutManager = LinearLayoutManager(this)
        personList.adapter = a
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                personViewModel.updateViewState()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
