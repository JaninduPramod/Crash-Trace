package com.crashtrace.mobile.data.Utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val ROLE_KEY = stringPreferencesKey("role")
    }

    // Save JWT token and role
    suspend fun saveUserData(jwtToken: String, role: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN_KEY] = jwtToken
            preferences[ROLE_KEY] = role
        }
    }

    // Remove JWT token
    suspend fun removeJwtToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(JWT_TOKEN_KEY)
        }
    }

    // Retrieve JWT token
    val jwtToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[JWT_TOKEN_KEY]
    }

    // Retrieve role
    val role: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ROLE_KEY]
    }
}