package com.example.kotlin.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin.R
import com.example.kotlin.ui.fragments.AuthFragment
import com.example.kotlin.ui.fragments.MainFragment
import com.example.kotlin.ui.fragments.OnBackPressedListener
import com.example.kotlin.ui.fragments.PersonFragment
import com.firebase.ui.auth.AuthUI
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu) = MenuInflater(this)
        .inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    private fun showLogoutDialog(){
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(getString(R.string.logout_dialog_ok)) { onLogout() }
            negativeButton(getString(R.string.logout_dialog_cancel)) {dialog -> dialog.dismiss() }
        }.show()
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

   private fun onLogout() {
        AuthUI.getInstance().signOut(this)
            .addOnCompleteListener {
                replaceFragment(AuthFragment())
            }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var backPressedListener: OnBackPressedListener? = null
        for(fragment in fm.fragments) {
            when(fragment){
                is OnBackPressedListener -> backPressedListener = fragment as OnBackPressedListener
            }
        }
        backPressedListener?.let {
            backPressedListener.onBackPressed()
            super.onBackPressed() } ?: super.onBackPressed()
    }
}
