package com.example.testapplication.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val KEY_JOKE_DATA = "_joke_data"
        const val IS_EXISTING_USER = "is_existing_user"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("SAMPLE_TEST_APP", Context.MODE_PRIVATE)
    private val mTypeTokenJokeListModel =
        object : TypeToken<MutableList<String>>() {}.type

    internal fun clearData() = sharedPref.edit { clear() }

    fun getIsExistingUser(): Boolean {
        return sharedPref.getBoolean(IS_EXISTING_USER, false)
    }

    fun saveJokeData(jokeData: String) {
        val jokeList = getJokeData()
        jokeList.add(jokeData)
        val valueString = Gson().toJson(jokeList, mTypeTokenJokeListModel)
        sharedPref.edit {
            putBoolean(IS_EXISTING_USER, true)
            putString(KEY_JOKE_DATA, valueString)
        }
    }

    fun getJokeData(): MutableList<String> {
        return sharedPref.getString(KEY_JOKE_DATA, null)?.let {
            Gson().fromJson(it, mTypeTokenJokeListModel)
        } ?: run { mutableListOf() }
    }

    fun read(key: String?, defValue: String?): String? {
        return sharedPref.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defValue)
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }
}