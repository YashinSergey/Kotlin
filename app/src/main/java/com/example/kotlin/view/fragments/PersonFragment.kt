package com.example.kotlin.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import com.example.kotlin.view.MainActivity
import com.example.kotlin.viewmodel.PersonViewModel
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class PersonFragment : Fragment() {

    private lateinit var activity: MainActivity
    private var person: Person? = null
    lateinit var personViewModel: PersonViewModel
    lateinit var tvLastChangeDate: TextView
    lateinit var fullName: TextInputEditText
    lateinit var personDescription: EditText

    companion object {
        private const val DATE_TIME_FORMAT = "dd.mm.yy HH:mm"

        fun start(activity: MainActivity, person: Person) {
            val bundle: Bundle? = Bundle()
            bundle?.putParcelable(MainFragment.KEY, person)
            activity.personFragment?.let { activity.replaceFragment(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        activity = getActivity() as MainActivity
        val bundle = arguments
        person = bundle?.getParcelable(MainFragment.KEY)
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel::class.java)

        initViews(view)

        setTextViewLastChangeDate()

        return view
    }

    private fun setTextViewLastChangeDate() {
        tvLastChangeDate.text = if (person != null) {
            setLastChangeDate(person!!.lastChanged)
        } else {
            setLastChangeDate(Date())
        }
    }

    private fun setLastChangeDate(date: Date): String {
        return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date)
    }

    private fun initViews(view: View) {
        tvLastChangeDate = view.findViewById(R.id.tvLastChangeDate)
        fullName = view.findViewById(R.id.fullName)
        personDescription = view.findViewById(R.id.personDescription)

        fullName.removeTextChangedListener(textChangeListener)
        personDescription.removeTextChangedListener(textChangeListener)

        if (person != null) {
            fullName.setText(person?.name ?: "")
            personDescription.setText(person?.description ?: "")
            val color = when(person!!.color) {
                Person.Color.WHITE -> R.color.white
                Person.Color.DARK_WHITE -> R.color.darkWhite
            }
            view.setBackgroundColor(resources.getColor(color))
        }

        fullName.addTextChangedListener(textChangeListener)
        personDescription.addTextChangedListener(textChangeListener)
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
           savePerson()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    fun savePerson() {
        if (fullName.text == null || fullName.text!!.length < 3) return

        person = person?.copy(
            name = fullName.text.toString(),
            description = personDescription.text.toString(),
            lastChanged = Date()
        ) ?: Person(
            UUID.randomUUID().toString(),
            fullName.text.toString(),
            personDescription.text.toString(),
            lastChanged = Date()
        )
       person?.let { personViewModel.save(it) }
    }
}




