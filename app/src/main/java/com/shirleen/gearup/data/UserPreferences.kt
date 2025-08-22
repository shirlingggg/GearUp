package com.shirleen.gearup.data


import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Step 1: Create a DataStore instance (this only needs to exist once in the app)
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private val ROLE_KEY = stringPreferencesKey("user_role")

    // Step 2: Save user role
    suspend fun saveUserRole(context: Context, role: String) {
        context.dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    // Step 3: Get user role
    fun getUserRole(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ROLE_KEY]
        }
    }
}
