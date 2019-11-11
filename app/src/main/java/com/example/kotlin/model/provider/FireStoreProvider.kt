package com.example.kotlin.model.provider

import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.PersonResult
import com.example.kotlin.model.entity.Person
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FireStoreProvider: RemoteDataProvider {

    companion object {
        private const val PERSONS_COLLECTION = "persons"
    }

    private var whiteColor: Boolean = true
    private val store by lazy { FirebaseFirestore.getInstance() }
    private val personsReference by lazy { store.collection(PERSONS_COLLECTION) }

    override fun subscribeToAllPersons() = MutableLiveData<PersonResult>().apply {
        personsReference.addSnapshotListener {snapshot, e ->
            e?.let {value = PersonResult.Error(it)}
                ?: let{snapshot?.let {
                    val persons = mutableListOf<Person>()
                    for (doc: QueryDocumentSnapshot in snapshot) {
                        persons.add(doc.toObject(Person::class.java))
                    }
                    value = PersonResult.Success(persons)
                }}
        }
    }

    override fun getPersonById(id: String) = MutableLiveData<PersonResult>().apply  {
        personsReference.document(id).get()
            .addOnSuccessListener { documentSnapshot ->
            value = PersonResult.Success(documentSnapshot.toObject(Person::class.java))
        }
            .addOnFailureListener { value = PersonResult.Error(it) }
    }

    override fun savePerson(person: Person) = MutableLiveData<PersonResult>().apply {
        personsReference.document(person.id)
            .set(person)
            .addOnSuccessListener { Timber.d {"Person $person is saved"}
            value = PersonResult.Success(person) }
            .addOnFailureListener { Timber.d {"Error saving $person, message: ${it.message}"}
            value = PersonResult.Error(it)}
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