package com.example.kotlin.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Person(val id: String, val name: String, val description: String,
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
        WHITE, DARK_WHITE
    }
}