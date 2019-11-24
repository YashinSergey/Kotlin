package com.example.kotlin.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import com.example.kotlin.R
import com.example.kotlin.common.getColorInt
import com.example.kotlin.model.entity.Person
import com.example.kotlin.ui.viewstates.PersonData
import com.example.kotlin.viewmodels.PersonViewModel
import kotlinx.android.synthetic.main.color_circle_view.*
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class PersonFragment : BaseFragment<PersonData>(), OnBackPressedListener {

    override val viewModel: PersonViewModel by viewModel()

    private var person: Person? = null
    private var color = Person.Color.WHITE

    companion object {
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        const val KEY = "person"
        fun newInstance(personId: String?) = PersonFragment().apply {
            arguments = Bundle().apply { putString(KEY, personId) }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPersonIfNotNull()
        colorPicker.onColorClickListener = {
            color = it
            view.setBackgroundColor(color.getColorInt(activity.applicationContext))
            savePerson()
        }
        initViews()
        setTextViewLastChangeDate()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater
        .inflate(R.menu.menu_person, menu).let { menu.findItem(R.id.logout).isVisible = false }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.palette -> { togglePalette().let { true } }
        R.id.delete -> { deletePerson().let { true } }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) colorPicker.close()
        else colorPicker.open()
    }

    private fun deletePerson() {
      activity.alert {
          messageResource = R.string.person_delete_message
          negativeButton(R.string.person_delete_cancel) {dialog -> dialog.dismiss()}
          positiveButton(R.string.person_delete_ok) {viewModel.deletePerson()}
      }.show()
    }

    private fun setPersonIfNotNull() {
        val bundle = this.arguments
        val personId = bundle?.getString(KEY)

        personId?.let {
            viewModel.loadPerson(it)
        }
    }

    override fun renderData(data: PersonData) {
        if(data.isDeleted) {
            activity.supportFragmentManager.popBackStack()
            return
        }
        this.person = data.person
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

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        person?.let {person ->
            tvLastChangeDate?.text = "  ${person.lastChanged}"
            fullName.setText(person.name.replace(":", ""))
            personDescription.setText(person.description)
            this.view?.setBackgroundColor(person.color.getColorInt(activity.applicationContext))
        }
    }

    private fun savePerson() {
        if (fullName.text == null || fullName.text!!.length < 3) return

        person = person?.copy(
            name = fullName.text.toString(),
            description = personDescription.text.toString(),
            color = color,
            lastChanged = Date()
        ) ?: Person(
            UUID.randomUUID().toString(),
            fullName.text.toString(),
            personDescription.text.toString(),
            color = color
        )
        person?.let { viewModel.save(it) }
    }

    override fun onBackPressed() {
        savePerson()
    }
}




