package com.sundayamuke.findadev.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sundayamuke.findadev.model.Dev

class EditViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val _eventUpdateSuccessful = MutableLiveData<Boolean>()
    val eventUpdateSuccessful: LiveData<Boolean>
        get() = _eventUpdateSuccessful

     fun updateUser(newUser: Dev) {

        db.collection("users").document(newUser.email)
            .set(newUser)
            .addOnSuccessListener {
                _eventUpdateSuccessful.value = true
            }
            .addOnFailureListener { e ->
                _eventUpdateSuccessful.value = false
            }
    }
}