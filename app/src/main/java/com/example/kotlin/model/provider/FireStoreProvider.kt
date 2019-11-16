package com.example.kotlin.model.provider

import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.example.kotlin.model.entity.User
import com.example.kotlin.model.errors.AuthException
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore): RemoteDataProvider {

    companion object {
        private const val PERSONS_COLLECTION = "persons"
        private const val USERS_COLLECTION = "users"
    }

    private var whiteColor: Boolean = true
    private val user
        get() = firebaseAuth.currentUser

    override fun getCurrentUser() = MutableLiveData<User?>().apply {
        value = user?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    private fun getUserPersonsCollection() = user?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(PERSONS_COLLECTION)
    } ?: throw AuthException()

    override fun subscribeToAllPersons() = MutableLiveData<PersonResult>().apply {
        try {
            getUserPersonsCollection().addSnapshotListener {snapshot, e ->
                e?.let {value = PersonResult.Error(it)}
                    ?: let{snapshot?.let {
                        val persons = mutableListOf<Person>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            persons.add(doc.toObject(Person::class.java))
                        }
                        value = PersonResult.Success(persons)
                    }}
            }
        } catch (error: Throwable){
            value = PersonResult.Error(error)
        }
    }

    override fun getPersonById(id: String) = MutableLiveData<PersonResult>().apply  {
        try {
            getUserPersonsCollection().document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    value = PersonResult.Success(documentSnapshot.toObject(Person::class.java))
                }
                .addOnFailureListener { value = PersonResult.Error(it) }
        } catch (error: Throwable){
            value = PersonResult.Error(error)
        }
    }

    override fun savePerson(person: Person) = MutableLiveData<PersonResult>().apply {
        try {
            getUserPersonsCollection().document(person.id)
                .set(person)
                .addOnSuccessListener { Timber.d {"Person $person is saved"}
                    value = PersonResult.Success(person) }
                .addOnFailureListener { Timber.d {"Error saving $person, message: ${it.message}"}
                    value = PersonResult.Error(it)}
        } catch (error: Throwable){
            value = PersonResult.Error(error)
        }
    }

    override fun setColor(): Person.Color {
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
}