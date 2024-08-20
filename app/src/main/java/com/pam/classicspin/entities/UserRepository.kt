package com.pam.classicspin.entities

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>) {
   private companion object {
       val USER_COINS = intPreferencesKey("user_coins")
   }

   val currentUserCoins: Flow<Int> =
       dataStore.data.map { preferences ->
           preferences[USER_COINS] ?: 0
       }

    suspend fun changeUserCoins(coins: Int) {
        dataStore.edit { preferences ->
            preferences[USER_COINS] = coins
        }
    }
}