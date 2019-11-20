package com.example.kotlin.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.repository.PersonsRepos
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockPersonsRepos = mockk<PersonsRepos>()
    private val personsLiveData = MutableLiveData<PersonResult>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        every { mockPersonsRepos.getPersons() } returns personsLiveData
        viewModel = MainViewModel(mockPersonsRepos)
    }

    @Test
    fun `should call getPersons once`() {
        verify(exactly = 1) {mockPersonsRepos.getPersons()}
    }

    @Test
    fun `should return persons`() {
        var result: List<Person>? = null
        val testData = listOf(Person("1"), Person("2"))
        viewModel.getViewState().observeForever {
            result = it.data
        }
        personsLiveData.value = PersonResult.Success(data = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should return error`() {
        var result: Throwable? = null
        val testError = Throwable("some error message")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        personsLiveData.value = PersonResult.Error(error = testError)
        assertEquals(testError, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        assertFalse(personsLiveData.hasObservers())
    }

}