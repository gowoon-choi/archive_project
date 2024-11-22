package com.nexters.fullstack.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.nexters.fullstack.App
import kotlinx.coroutines.flow.map

class PrefDataStoreManager {
    private val dataStore = App.app.createDataStore(name = DATA_STORE_NAME)

    fun readIsFirstFlow() = dataStore.data.map {
        it[IS_FIRST] ?: true
    }

    suspend fun updateIsFirst(isFirst : Boolean){
        dataStore.edit {
            it[IS_FIRST] = isFirst
        }
    }

    companion object{
        private const val DATA_STORE_NAME = "on_boarding"
        private const val IS_FIRST_KEY = "is_first"
        private val IS_FIRST = booleanPreferencesKey(IS_FIRST_KEY)
    }
}