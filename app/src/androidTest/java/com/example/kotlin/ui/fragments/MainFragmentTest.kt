package com.example.kotlin.ui.fragments

import android.app.PendingIntent.getActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before

import org.junit.Rule
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.example.kotlin.R
import com.example.kotlin.model.entity.Person
import com.example.kotlin.ui.MainActivity
import com.example.kotlin.ui.adapters.MainAdapter
import com.example.kotlin.ui.viewstates.MainViewState
import com.example.kotlin.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin

class MainFragmentTest {

    @get:Rule
    val mainFragmentRule: FragmentTestRule<*, MainFragment> = FragmentTestRule.create(MainFragment::class.java)

    private val mainViewModel = mockk<MainViewModel>()
    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testPersons= listOf(
        Person("1", "title 1", "description 1"),
        Person("2", "title 2", "description 2"),
        Person("3", "title 3", "description 3"))

    @Before
    fun setUp() {
        loadKoinModules (listOf(module { viewModel(override = true) { mainViewModel } }))
        every { mainViewModel.getViewState() } returns viewStateLiveData
        every { mainViewModel.onCleared() } just runs

        mainFragmentRule.launchActivity(null)
        mainFragmentRule.launchFragment(MainFragment())
        viewStateLiveData.postValue(MainViewState(persons = testPersons))
        Espresso.getIdlingResources()
    }

    @Test
    fun checkDataIsDisplayed(){
        onView(withId(R.id.personsList)).perform(scrollToPosition<MainAdapter.PersonViewHolder>(1))
        onView(withText(testPersons[1].description)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}