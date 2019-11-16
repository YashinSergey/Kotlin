package com.example.kotlin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.common.getColorInt
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_person.view.*

class MainAdapter(val onItemClick: ((Person) -> Unit)? = null) : RecyclerView.Adapter<MainAdapter.PersonViewHolder>() {

    private var persons: List<Person> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false))
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    fun refreshPersonsList(list: List<Person>) {
        this.persons = list
        notifyDataSetChanged()
    }

    inner class PersonViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(person: Person) = with(itemView) {
            userName.text = person.name
            userDescription.text = person.description
            val color = person.color.getColorInt(itemView.context)
            setBackgroundColor(color)

            itemView.setOnClickListener {
                onItemClick?.invoke(person)
            }
        }
    }
}