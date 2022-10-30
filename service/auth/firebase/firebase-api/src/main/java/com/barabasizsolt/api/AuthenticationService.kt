package com.barabasizsolt.api

import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import kotlinx.coroutines.flow.Flow

sealed class AuthenticationState {
    object Idle : AuthenticationState()
    object Logged : AuthenticationState()
    object NotLogged : AuthenticationState()
}

interface AuthenticationService {

    val authenticationState: Flow<AuthenticationState>

    fun initialize(context: Context)

    fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    fun registerWithEmailAndPassWord(email: String, password: String): Flow<AuthResult>

    fun loginWithGoogleAccount(intent: Intent): Flow<AuthResult>

    fun getIntentForGoogleAccountLogin(): Intent

    fun registerFacebookCallbackManager(requestCode: Int, resultCode: Int, data: Intent?)

    fun loginWithFacebookAccount(): Flow<AuthResult>

    fun logOut()

    fun resetPassword(email: String): Flow<AuthResult>
}
