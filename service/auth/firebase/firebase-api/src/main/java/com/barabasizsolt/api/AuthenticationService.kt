package com.barabasizsolt.api

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface AuthenticationService {

    fun isLogged(): Boolean

    fun initialize(context: Context)

    fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    fun registerWithEmailAndPassWord(email: String, password: String): Flow<AuthResult>

    fun loginWithGoogleAccount(intent: Intent): Flow<AuthResult>

    fun getIntentForGoogleAccountLogin(): Intent

    fun logOut(): Flow<AuthResult>

    fun resetPassword(email: String): Flow<AuthResult>
}