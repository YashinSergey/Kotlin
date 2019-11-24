package com.example.kotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person

private var whiteColor: Boolean = true

fun Person.Color.getColorInt(context: Context): Int = ContextCompat.getColor(context, getColorRes())

fun Person.Color.getColorRes(): Int = when (setColor()) {
    Person.Color.WHITE -> R.color.white
    Person.Color.WHITE_DARK -> R.color.white_dark
}


fun setColor(): Person.Color {
    var color = Person.Color.WHITE
    when (whiteColor) {
        true -> whiteColor = false
        false -> {
            color = Person.Color.WHITE_DARK
            whiteColor = true
        }
    }
    return color
}