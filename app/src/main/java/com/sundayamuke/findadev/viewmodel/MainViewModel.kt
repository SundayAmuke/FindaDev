package com.sundayamuke.findadev.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.model.Dev

const val RC_SIGN_IN = 1

class MainViewModel(private val app: Application): AndroidViewModel(app) {
    // Authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    private val _user = MutableLiveData<Dev>()
    val user: LiveData<Dev>
        get() = _user

    private val _devs = MutableLiveData<MutableList<Dev>>()
    val devs: LiveData<MutableList<Dev>>
        get() = _devs

    private val db = Firebase.firestore
    private val collectionRef = db.collection("users")
    private val auth = FirebaseAuth.getInstance()
    lateinit var authListener: FirebaseAuth.AuthStateListener

    init {
        _user.value = Dev()
        _devs.value = mutableListOf()


        collectionRef.addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w("MainViewModel", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                _devs.value?.clear()

                for (document in snapshot.documents) {
                    val aDev = document.toObject(Dev::class.java)!!
                    if (aDev.email != user.value!!.email) {
                        _devs.addNewItem(aDev)
                    }
                }
            } else {
                Log.d("MainViewModel", "Current data: null")
            }
        }
    }

     fun setupSignIn(user: FirebaseUser) {
         setName(user.displayName)
         setUrl(user.photoUrl)
         val email = user.email
         setEmail(email)


         collectionRef.document(email!!).addSnapshotListener{ value, e ->
             if (e != null) {
                 return@addSnapshotListener
             }

             if (value != null) {
                 _user.value = value.toObject(Dev::class.java)
                 Log.i("Ye", "ye")
             }
         }
    }

    private fun createUser() {
        val devToAdd = user.value!!

        collectionRef.document(devToAdd.email)
            .set(devToAdd)
            .addOnSuccessListener {
                Log.d("MainFragment", "Successfully created")
            }
            .addOnFailureListener { e ->
                Log.w("MainFragment", "Error adding document", e)
            }
    }

    private fun setEmail(email: String?) {
        _user.value!!.email = email!!
    }

    private fun setUrl(uri: Uri?) {
        _user.value!!.photoUrl =  when (uri) {
            null -> ""
            else -> uri.toString()
        }
    }

    private fun setName(name: String?) {
        _user.value!!.fullName = when (name) {
            null -> app.getString(R.string.no_name_text)
            else -> name
        }
    }

    private fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.value = oldValue
    }

    fun addAuthListener() {
        auth.addAuthStateListener(authListener)
    }

    fun removeAuthListener() {
        auth.removeAuthStateListener(authListener)
    }
}