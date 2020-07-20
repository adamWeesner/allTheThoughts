package com.weesnerdevelopment.allthethoughts

import android.os.Parcelable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thought(
    val id: String? = null,
    val owner: String? = null,
    val time: Long? = System.currentTimeMillis(),
    val value: String? = null
) : Parcelable

interface Backend {
    fun add(thought: Thought, callback: () -> Unit)
    fun getAll(callback: (List<Thought>?) -> Unit)
}

class FireBackend(
    private val user: User
) : Backend {
    override fun add(thought: Thought, callback: () -> Unit) {
        Firebase.firestore
            .collection(user.id)
            .apply {
                val doc = document()
                add(thought.copy(id = doc.id))
                    .addOnSuccessListener {
                        println("Successfully added thought ${it.id}")
                        callback()
                    }
                    .addOnFailureListener { println("Failed to save thought ${it.message}") }
            }
    }

    override fun getAll(callback: (List<Thought>?) -> Unit) {
        Firebase.firestore
            .collection(user.id)
            .get()
            .addOnSuccessListener { callback(it.toObjects(Thought::class.java)) }
            .addOnFailureListener { println("getting items failed ${it.message}") }
    }
}
