package jp.ac.mayoi.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val dataStoreName = "maigo-preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = dataStoreName)

class UserInfoDataStoreWrapper(
    private val appContext: Context
) {
    fun getUserId(): Flow<String> = appContext.dataStore.data.map { preferences ->
        preferences[userId] ?: ""
    }

    suspend fun setUserId(newUserId: String) {
        appContext.dataStore.edit { settings ->
            settings[userId] = newUserId
        }
    }

    private companion object PreferenceKey {
        val userId = stringPreferencesKey("user_id")
    }
}
