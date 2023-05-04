package com.barabasizsolt.mova.auth.api

import kotlinx.coroutines.flow.Flow

interface IosX64MainAuthenticationService : AuthenticationService {

    override val authenticationState: Flow<AuthenticationState>

    override fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    override fun registerWithEmailAndPassWord(email: String, password: String): Flow<AuthResult>

    override suspend fun logOut()

    override suspend fun resetPassword(email: String)
}