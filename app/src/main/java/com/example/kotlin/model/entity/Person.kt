package com.example.kotlin.model.entity

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.example.kotlin.R
import com.example.kotlin.model.repository.PersonsRepos
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Person(val id: String = "", val name: String = "", val description: String = "",
                  val color: Color = Color.WHITE, val lastChanged: Date = Date()) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Person
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + lastChanged.hashCode()
        return result
    }

    enum class Color {
        WHITE, WHITE_DARK;

        fun getColorInt(context: Context) :Int = ContextCompat.getColor(context,
            when(PersonsRepos.setColor()) {
                WHITE -> R.color.white
                WHITE_DARK -> R.color.white_dark
            })

        fun getColorRes() :Int =
            when(PersonsRepos.setColor()) {
                WHITE -> R.color.white
                WHITE_DARK -> R.color.white_dark
            }
    }
}