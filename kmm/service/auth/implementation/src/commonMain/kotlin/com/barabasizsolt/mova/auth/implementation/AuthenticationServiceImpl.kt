package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.AuthResult
import com.barabasizsolt.mova.auth.api.AuthenticationService
import com.barabasizsolt.mova.auth.api.AuthenticationState
import com.barabasizsolt.mova.auth.api.authenticate
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

open class AuthenticationServiceImpl : AuthenticationService, KoinComponent {

    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val _authenticationState = MutableStateFlow<AuthenticationState>(value = AuthenticationState.Idle)
    override val authenticationState: Flow<AuthenticationState> = _authenticationState

    init {
        _authenticationState.value = if (firebaseAuth.currentUser == null)
            AuthenticationState.NotLogged
        else
            AuthenticationState.Logged
    }

    fun getFirebaseAuth(): FirebaseAuth = firebaseAuth

    fun setAuthenticationState(state: AuthenticationState) {
        _authenticationState.value = state
    }

    override fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResult> = authenticate(
        authFunction = { firebaseAuth.signInWithEmailAndPassword(email, password) },
        sideEffect = { _authenticationState.value = AuthenticationState.Logged }
    )

    override fun registerWithEmailAndPassWord(email: String, password: String): Flow<AuthResult> = authenticate(
        authFunction = { firebaseAuth.createUserWithEmailAndPassword(email, password) },
        sideEffect = { _authenticationState.value = AuthenticationState.Logged }
    )

    override suspend fun logOut() {
        firebaseAuth.signOut()
        _authenticationState.value = AuthenticationState.NotLogged
    }

    override suspend fun resetPassword(email: String): Unit =
        firebaseAuth.sendPasswordResetEmail(email)


    companion object {
        const val WEB_CLIENT_ID: String = "693786424707-cecd7fpq40ppp33369sd26728po0u0ed.apps.googleusercontent.com"
    }
}
