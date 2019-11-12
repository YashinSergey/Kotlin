package com.example.kotlin.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin.R
import com.example.kotlin.ui.auth.LogoutDialog
import com.example.kotlin.ui.fragments.AuthFragment
import com.example.kotlin.ui.fragments.MainFragment
import com.example.kotlin.ui.fragments.PersonFragment
import com.firebase.ui.auth.AuthUI


class MainActivity : AppCompatActivity() , LogoutDialog.LogoutListener {

    companion object {
        private const val RC_SIGN_IN = 9646
    }

    lateinit var authFragment: AuthFragment
    lateinit var mainFragment: MainFragment
    lateinit var personFragment: PersonFragment
    var authIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authIntent = intent
        setContentView(R.layout.activity_main)
        initFragments(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    private fun showLogoutDialog(){
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
                LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        authFragment = AuthFragment()
        mainFragment = MainFragment()
        personFragment = PersonFragment()
        if (savedInstanceState == null) {
            replaceFragment(authFragment)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentsContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()
            , RC_SIGN_IN
        )
    }

    override fun onLogout() {
        AuthUI.getInstance().signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }
}
