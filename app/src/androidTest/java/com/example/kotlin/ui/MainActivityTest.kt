package com.example.kotlin.ui

import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule

class MainActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java,true, false)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
}