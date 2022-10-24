package com.barabasizsolt.firebase.implementation

import android.content.Context
import android.content.Intent
import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.api.AuthWithResult
import com.barabasizsolt.api.AuthenticationService
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.api.consumeTask
import com.barabasizsolt.api.consumeTaskWithResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

class AuthenticationServiceImpl : AuthenticationService {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleAuth: GoogleSignInClient

    private val _authenticationState = MutableStateFlow<AuthenticationState>(value = AuthenticationState.Idle)
    override val authenticationState: Flow<AuthenticationState> = _authenticationState

    override fun initialize(context: Context) {

        val request = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleAuth = GoogleSignIn.getClient(context, request)
        firebaseAuth = FirebaseAuth.getInstance()
        _authenticationState.value = if (firebaseAuth.currentUser == null) AuthenticationState.NotLogged else AuthenticationState.Logged
    }

    override fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResult> = consumeTask(
        task = firebaseAuth.signInWithEmailAndPassword(email, password),
        sideEffect = { _authenticationState.value = AuthenticationState.Logged }
    )

    override fun registerWithEmailAndPassWord(email: String, password: String): Flow<AuthResult> = consumeTask(
        task = firebaseAuth.createUserWithEmailAndPassword(email, password),
        sideEffect = { _authenticationState.value = AuthenticationState.Logged }
    )

    override fun getIntentForGoogleAccountLogin(): Intent {
        return googleAuth.signInIntent
    }

    override fun loginWithGoogleAccount(intent: Intent): Flow<AuthResult> = consumeTaskWithResult(
        task = GoogleSignIn.getSignedInAccountFromIntent(intent),
        taskConverter = { account -> GoogleAuthProvider.getCredential(account.idToken, null) }
    ).transform { result ->
        when (result) {
            is AuthWithResult.Success ->
                consumeTask(
                    task = firebaseAuth.signInWithCredential(result.data),
                    sideEffect = { _authenticationState.value = AuthenticationState.Logged }
                ).collect { res -> emit(res) }
            is AuthWithResult.Failure ->
                emit(value = AuthResult.Failure(error = result.error))
        }
    }

    override fun logOut() {
        googleAuth.signOut()
        firebaseAuth.signOut()
        _authenticationState.value = AuthenticationState.NotLogged
    }

    override fun resetPassword(email: String): Flow<AuthResult> = consumeTask(
        task = firebaseAuth.sendPasswordResetEmail(email)
    )

    companion object {
        private const val WEB_CLIENT_ID: String = "693786424707-cecd7fpq40ppp33369sd26728po0u0ed.apps.googleusercontent.com"
    }
}
