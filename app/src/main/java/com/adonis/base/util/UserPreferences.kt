package com.adonis.base.util


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject
constructor(private val dataStore: DataStore<Preferences>) {

    private val isUserNew = booleanPreferencesKey("IS_USER_NEW")

    suspend fun setIsUserNew(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[isUserNew] = boolean
        }
    }

    val getIsUserNew : Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[isUserNew] ?: true
    }

}