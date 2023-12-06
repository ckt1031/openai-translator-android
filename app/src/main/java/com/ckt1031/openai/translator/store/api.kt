package com.ckt1031.openai.translator.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class APIDataStoreKeys(val value: String) {
    OpenAIKey(("openai_key")),
    OpenAIHost(("openai_host")),
    OpenAIChatModel(("openai_chat_model")),
    OpenAIEnableStream(("openai_enable_stream")),
    OpenAIVoiceSpeaker(("openai_voice_speaker")),
    OpenAICustomChatModel(("openai_custom_chat_model")),
    SpeechToTextEngine(("stt_engine")),
}

class APIDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun saveStringPreference(keyString: APIDataStoreKeys, value: String) {
        val key = stringPreferencesKey(keyString.value)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun readStringPreference(keyString: APIDataStoreKeys): Flow<String?> {
        val key = stringPreferencesKey(keyString.value)
        return dataStore.data
            .map { preferences ->
                preferences[key]
            }
    }

    suspend fun saveBoolPreference(keyString: APIDataStoreKeys, value: Boolean) {
        val key = booleanPreferencesKey(keyString.value)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun readBoolPreference(keyString: APIDataStoreKeys): Flow<Boolean?> {
        val key = booleanPreferencesKey(keyString.value)
        return dataStore.data
            .map { preferences ->
                preferences[key]
            }
    }
}
