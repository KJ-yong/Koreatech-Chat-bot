package com.example.data.source.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
    @ApplicationContext applicationContext: Context
) {
    var masterKey = MasterKey.Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    var sharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        SHARED_PREF_FILENAME_TOKEN,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun saveAccessToken(
        accessToken: String
    ) = withContext(Dispatchers.IO) {
        with(sharedPreferences.edit()) {
            putString(SHARED_PREF_KEY_ACCESS_TOKEN, accessToken)
            apply()
        }
    }

    suspend fun saveFcmToken(
        fcmToken: String
    ) = withContext(Dispatchers.IO) {
        with(sharedPreferences.edit()) {
            putString(SHARED_PREF_KEY_FCM_TOKEN, fcmToken)
            apply()
        }
    }

    suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(SHARED_PREF_KEY_ACCESS_TOKEN, null)
    }

    suspend fun getFcmToken(): String? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(SHARED_PREF_KEY_FCM_TOKEN, null)
    }

    suspend fun removeAccessToken() = withContext(Dispatchers.IO) {
        with(sharedPreferences.edit()) {
            remove(SHARED_PREF_KEY_ACCESS_TOKEN)
            apply()
        }
    }

    companion object {
        private const val SHARED_PREF_FILENAME_TOKEN = "token"
        private const val SHARED_PREF_KEY_ACCESS_TOKEN = "accessToken"
        private const val SHARED_PREF_KEY_FCM_TOKEN = "fcmToken"
    }
}