package com.example.kotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person

fun Person.Color.getColorInt(context: Context): Int = ContextCompat.getColor(context, getColorRes())

fun Person.Color.getColorRes(): Int = when (this) {
    Person.Color.WHITE -> R.color.white
    Person.Color.WHITE_DARK -> R.color.white_dark
}