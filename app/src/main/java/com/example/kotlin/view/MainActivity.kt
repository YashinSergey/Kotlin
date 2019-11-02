package com.example.kotlin.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin.R
import com.example.kotlin.view.fragments.MainFragment
import com.example.kotlin.view.fragments.PersonFragment


class MainActivity : AppCompatActivity() {

    var mainFragment: MainFragment? = null
    var personFragment: PersonFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //TODO add something later
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        mainFragment = MainFragment()
        personFragment = PersonFragment()
        if (savedInstanceState == null) {
            replaceFragment(mainFragment!!)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentsContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
