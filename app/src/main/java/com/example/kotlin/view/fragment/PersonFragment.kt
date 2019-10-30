package com.example.kotlin.view.fragment

import android.app.Person
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin.R
import com.example.kotlin.view.MainActivity

class PersonFragment : Fragment() {

    private lateinit var activity: MainActivity

    companion object {
        private val EXTRA_PERSON = PersonFragment::class.java.name + "extra.PERSON"

        @RequiresApi(Build.VERSION_CODES.P)
        fun start(context: Context, person: Person?){
            val intent = Intent(context, PersonFragment::class.java)
            intent.putExtra(EXTRA_PERSON, person)
            context.startF
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        activity = getActivity() as MainActivity
        return view
    }
}