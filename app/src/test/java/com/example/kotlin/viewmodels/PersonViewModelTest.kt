package com.example.kotlin.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.repository.PersonsRepos
import com.example.kotlin.model.entity.Person
import com.example.kotlin.ui.viewstates.PersonViewState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PersonViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockPersonsRepos = mockk<PersonsRepos>(relaxed = true)
    private val personsLiveData = MutableLiveData<PersonResult>()
    private val testPerson = Person("1", "some title", "some description")

    private lateinit var viewModel: PersonViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        every { mockPersonsRepos.getPersonById(testPerson.id) } returns personsLiveData
        every { mockPersonsRepos.deletePerson(testPerson.id) } returns personsLiveData
        viewModel = PersonViewModel(mockPersonsRepos)
    }

    @Test
    fun `loadPerson should return person data`() {
        var result: PersonViewState.Data? = null
        var testData = PersonViewState.Data(false, testPerson)
        viewModel.getViewState().observeForever {
            result = it.data
        }
        viewModel.loadPerson(testPerson.id)
        personsLiveData.value = PersonResult.Success(testPerson)
        assertEquals(testData, result)
    }

    @Test
    fun `loadPerson should return error`() {
        var result: Throwable? = null
        val testError = Throwable("some error message")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        viewModel.loadPerson(testPerson.id)
        personsLiveData.value = PersonResult.Error(testError)
        assertEquals(testError, result)
    }

    @Test
    fun `deletePerson should return person data with isDeleted flag`() {
        var result: PersonViewState.Data? = null
        val testData = PersonViewState.Data(true, null)
        viewModel.getViewState().observeForever {
            result = it.data
        }
        viewModel.save(testPerson)
        viewModel.deletePerson()
        personsLiveData.value = PersonResult.Success(null)
        assertEquals(testData, result)
    }

    @Test
    fun `deletePerson should return error`() {
        var result: Throwable? = null
        val testError = Throwable("some error message")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        viewModel.save(testPerson)
        viewModel.deletePerson()
        personsLiveData.value = PersonResult.Error(testError)
        assertEquals(testError, result)
    }

    @Test
    fun `should save changes`() {
        viewModel.save(testPerson)
        viewModel.onCleared()
        verify(exactly = 1) { mockPersonsRepos.savePerson(testPerson) }
    }
}