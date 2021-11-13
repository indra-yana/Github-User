package com.submission.githubuser1.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/****************************************************
 * Created by Indra Muliana
 * On Tuesday, 09/11/2021 20.57
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class AppPreferences(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Boolean> = dataStore.data.map {
        it[THEME_KEY] ?: false
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) = dataStore.edit {
        it[THEME_KEY] = isDarkModeActive
    }

    companion object {
        private val THEME_KEY = booleanPreferencesKey("theme_setting")

        @Volatile
        private var INSTANCE: AppPreferences? = null

        fun initPreferences(dataStore: DataStore<Preferences>): AppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}