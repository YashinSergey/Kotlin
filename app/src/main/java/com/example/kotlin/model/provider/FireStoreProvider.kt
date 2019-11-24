package com.example.kotlin.model.provider

import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.entity.User
import com.example.kotlin.model.errors.AuthException
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore): RemoteDataProvider {

    companion object {
        private const val PERSONS_COLLECTION = "persons"
        private const val USERS_COLLECTION = "users"
    }

    private val user
        get() = firebaseAuth.currentUser

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        continuation.resume(
            user?.let { User(it.displayName ?: "", it.email ?: "")})
    }

    private fun getUserPersonsCollection() = user?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(PERSONS_COLLECTION)
    } ?: throw AuthException()

    @ExperimentalCoroutinesApi
    override fun subscribeToAllPersons(): ReceiveChannel<PersonResult> = Channel<PersonResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null
        try {
            registration = getUserPersonsCollection().addSnapshotListener {snapshot, e ->
                val value = e?.let { PersonResult.Error(it) }
                    ?: let{snapshot?.let {
                        val persons = it.documents.map { it.toObject(Person::class.java) }
                        PersonResult.Success(persons)
                    }}
                value?.let { offer(it) }
            }
        } catch (error: Throwable){
            offer(PersonResult.Error(error))
        }
        invokeOnClose { registration?.remove() }
    }

    override suspend fun getPersonById(id: String): Person = suspendCoroutine { continuation ->
        try {
            getUserPersonsCollection().document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    continuation.resume(documentSnapshot.toObject(Person::class.java)!!)
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (error: Throwable){
            continuation.resumeWithException(error)
        }
    }

    override suspend fun savePerson(person: Person): Person = suspendCoroutine { continuation ->
        try {
            getUserPersonsCollection().document(person.id)
                .set(person)
                .addOnSuccessListener { Timber.d {"Person $person is saved"}
                    continuation.resume(person) }
                .addOnFailureListener { Timber.d {"Error saving $person, message: ${it.message}"}
                    continuation.resumeWithException(it)}
        } catch (error: Throwable){
            continuation.resumeWithException(error)
        }
    }

    override suspend fun deletePerson(personId: String): Unit = suspendCoroutine { continuation ->
        try {
            getUserPersonsCollection().document(personId)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(Unit) }
                .addOnFailureListener {
                    continuation.resumeWithException(it)}
        } catch (error: Throwable){
            continuation.resumeWithException(error)
        }
    }

}