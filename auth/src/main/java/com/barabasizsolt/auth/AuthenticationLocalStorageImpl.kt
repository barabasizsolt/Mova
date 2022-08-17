package com.barabasizsolt.auth

import android.content.Context
import com.halcyonmobile.oauth.dependencies.AuthenticationLocalStorage
import com.halcyonmobile.oauthstorage.AuthenticationSharedPreferencesStorage
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AuthenticationLocalStorageImpl constructor(
    private val authenticationLocalStorage: AuthenticationLocalStorage
) : AuthenticationLocalStorage by authenticationLocalStorage {

    constructor(context: Context) : this(
        authenticationLocalStorage = AuthenticationSharedPreferencesStorage(
            EncryptedSharedPreferences.create(
                context,
                "authentication_encrypted_shared_preferences",
                MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        )
    )
}