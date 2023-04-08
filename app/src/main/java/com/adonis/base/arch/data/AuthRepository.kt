package com.adonis.base.arch.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth : FirebaseAuth,
    private val db : FirebaseFirestore
) {

    suspend fun signInUserWithFirebase(){

    }

}