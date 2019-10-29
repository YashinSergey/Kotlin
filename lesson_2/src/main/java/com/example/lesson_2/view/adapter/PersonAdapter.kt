package com.example.lesson_2.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_2.view.MainViewState
import com.example.lesson_2.R
import com.example.lesson_2.model.entity.Person
import kotlinx.android.synthetic.main.person_item.view.*


class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonHolder>() {

    private var persons: List<Person> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        return PersonHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.person_item, parent, false)
        )
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bind(persons[position])
    }

    fun refreshPersonList(mainViewState: MainViewState) {
        this.persons = mainViewState.persons
        notifyDataSetChanged()
    }

    class PersonHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(person: Person) = with(itemView) {
            userName.text = person.name
            userDescription.text = person.description
            setBackgroundColor(person.color)
        }
    }
}