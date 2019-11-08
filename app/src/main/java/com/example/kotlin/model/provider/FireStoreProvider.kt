package com.example.kotlin.model.provider

import androidx.lifecycle.LiveData
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
    private val store = FirebaseFirestore.getInstance()
    private val personsReference = store.collection(PERSONS_COLLECTION)

    override fun subscribeToAllPersons(): LiveData<PersonResult> {
        val result = MutableLiveData<PersonResult>()
        personsReference.addSnapshotListener {snapshot, e ->
            e?.let {result.value = PersonResult.Error(it)}
                ?: let{snapshot?.let {
                    val persons = mutableListOf<Person>()
                    for (doc: QueryDocumentSnapshot in snapshot) {
                        persons.add(doc.toObject(Person::class.java))
                    }
                    result.value = PersonResult.Success(persons)
                }}
        }
        return result
    }

    override fun getPersonById(id: String): LiveData<PersonResult> {
        val result = MutableLiveData<PersonResult>()

        personsReference.document(id).get()
            .addOnSuccessListener { documentSnapshot ->
            result.value = PersonResult.Success(documentSnapshot.toObject(Person::class.java))
        }
            .addOnFailureListener { result.value = PersonResult.Error(it) }
        return result
    }

    override fun savePerson(person: Person): LiveData<PersonResult> {
        val result = MutableLiveData<PersonResult>()

        personsReference.document(person.id)
            .set(person)
            .addOnSuccessListener {
                Timber.d {"Person $person is saved"}
            result.value = PersonResult.Success(person)
        }.addOnFailureListener {
                Timber.d {"Error saving $person, message: ${it.message}"}
                result.value = PersonResult.Error(it)
            }
        return result
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