package com.example.kotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import kotlinx.android.synthetic.main.item_person.view.*

class MainAdapter(val onItemClick: ((Person) -> Unit)? = null) : RecyclerView.Adapter<MainAdapter.PersonHolder>() {

    private var persons: List<Person> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        return PersonHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false))
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bind(persons[position])
    }

    fun refreshPersonsList(list: List<Person>) {
        this.persons = list
        notifyDataSetChanged()
    }

    inner class PersonHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(person: Person) = with(itemView) {
            userName.text = person.name
            userDescription.text = person.description
            val color = when(person.color) {
                Person.Color.WHITE -> R.color.white
                Person.Color.DARK_WHITE -> R.color.darkWhite

            }
            setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener {
                onItemClick?.invoke(person)
            }
        }
    }
}