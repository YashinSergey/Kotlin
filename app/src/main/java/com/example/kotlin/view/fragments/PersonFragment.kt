package com.example.kotlin.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import com.example.kotlin.view.MainActivity
import com.example.kotlin.view.viewstates.PersonViewState
import com.example.kotlin.viewmodels.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person.*
import java.text.SimpleDateFormat
import java.util.*

class PersonFragment : BaseFragment<Person?, PersonViewState>() {
    override val viewModel: PersonViewModel by lazy { ViewModelProviders.of(this).get(PersonViewModel::class.java) }

    private lateinit var activity: MainActivity

    private var person: Person? = null

    companion object {

        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        const val KEY = "person"
        fun newInstance(personId: String?) = PersonFragment().apply {
            arguments = Bundle().apply { putString(KEY, personId) }
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        activity = getActivity() as MainActivity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPersonIfNotNull()
        initViews()
        setTextViewLastChangeDate()
    }

    private fun setPersonIfNotNull() {
        val bundle = this.arguments
        val personId = bundle?.getString(KEY)

        personId?.let {
            viewModel.loadPerson(it)
        }
    }

    override fun renderData(data: Person?) {
        this.person = data
        initViews()
    }

    private fun setTextViewLastChangeDate() {
        when {
            person != null -> setLastChangeDate(person!!.lastChanged)
            else -> setLastChangeDate(Date())
        }
    }

    private fun setLastChangeDate(date: Date): String {
        return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date)
    }

    private fun initViews() {
        fullName.removeTextChangedListener(textChangeListener)
        personDescription.removeTextChangedListener(textChangeListener)

        person?.let {person ->
            fullName.setText(person.name.replace(":", ""))
            personDescription.setText(person.description)
            val color = when(person.color) {
                Person.Color.WHITE -> R.color.white
                Person.Color.WHITE_DARK -> R.color.white_dark
            }
            this.view?.setBackgroundColor(resources.getColor(color))
        }

        fullName.addTextChangedListener(textChangeListener)
        personDescription.addTextChangedListener(textChangeListener)
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
            personDescription.text.toString()
        )
        person?.let { viewModel.save(it) }
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
           savePerson()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


}




