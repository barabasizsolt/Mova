package com.barabasizsolt.mova.auth.api

import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface AndroidAuthenticationService : AuthenticationService {

    fun loginWithGoogleAccount(intent: Intent): Flow<AuthResult>

    fun getIntentForGoogleAccountLogin(): Intent

    fun loginWithFacebookAccount(): Flow<AuthResult>

    fun registerFacebookCallbackManager(requestCode: Int, resultCode: Int, data: Intent?)
}