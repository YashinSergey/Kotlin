package com.example.kotlin.model.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.errors.AuthException
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val provider = FireStoreProvider(mockAuth, mockDb)

    private val testPersons = listOf(
        Person("1"),
        Person("2"),
        Person("3")
    )

    @Before
    fun setUp() {
        clearMocks(mockResultCollection, mockUser, mockDocument1, mockDocument2, mockDocument3)

        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every {
            mockDb.collection(any()).document(any()).collection(any())
        } returns mockResultCollection

        every { mockDocument1.toObject(Person::class.java) } returns testPersons[0]
        every { mockDocument2.toObject(Person::class.java) } returns testPersons[1]
        every { mockDocument3.toObject(Person::class.java) } returns testPersons[2]
    }

    @Test
    fun `throw AuthException`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllPersons().observeForever {
            result = (it as? PersonResult.Error)?.error
        }
        assertTrue(result is AuthException)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `subscribeToAllPersons return persons`() {
        var result: List<Person>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.subscribeToAllPersons().observeForever {
            result = (it as? PersonResult.Success<*>)?.data as List<Person>?
        }
        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(testPersons, result)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `subscribeToAllPersons return error`() {
        var result: Throwable? = null
        val testError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.subscribeToAllPersons().observeForever {
            result = (it as? PersonResult.Error)?.error
        }
        slot.captured.onEvent(null, testError)
        assertEquals(testError, result)
    }

    @Test
    fun `savePerson calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()

        every { mockResultCollection.document(testPersons[0].id) } returns mockDocumentReference
        provider.savePerson(testPersons[0])
        verify(exactly = 1) { mockDocumentReference.set(testPersons[0]) }
    }

    @Test
    fun `savePerson returns Person`() {
        var result: Person? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document(testPersons[0].id) } returns mockDocumentReference
        every {
            mockDocumentReference.set(testPersons[0]).addOnSuccessListener(capture(slot))
        } returns mockk()

        provider.savePerson(testPersons[0]).observeForever {
            result = (it as? PersonResult.Success<*>)?.data as Person?
        }
        slot.captured.onSuccess(null)
        assertEquals(testPersons[0], result)
    }

    @Test
    fun `deletePerson calls delete`() {
        val mockDocumentReference = mockk<DocumentReference>()

        every { mockResultCollection.document(testPersons[0].id) } returns mockDocumentReference
        provider.deletePerson(testPersons[0].id)
        verify(exactly = 1) { mockDocumentReference.delete() }
    }

    @After
    fun tearDown() {
    }
}
