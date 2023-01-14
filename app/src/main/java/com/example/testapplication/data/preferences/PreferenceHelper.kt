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
        const val KEY_USER_LOGIN_DATA = "_user_login_data"
        const val KEY_USER_AUTH_DATA = "_user_auth_data"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("NAYAN_CONTRACTOR", Context.MODE_PRIVATE)
//    private val mTypeTokenLoginModel = object : TypeToken<UserData>() {}.type
    internal fun clearData() = sharedPref.edit { clear() }

//    fun setUserData(userData: UserData?) {
//        sharedPref.edit {
//            putString(KEY_USER_LOGIN_DATA, Gson().toJson(userData, mTypeTokenLoginModel))
//        }
//    }
//
//    fun getUserData(): UserData? {
//        return sharedPref.getString(KEY_USER_LOGIN_DATA, null)?.let {
//            Gson().fromJson(it, mTypeTokenLoginModel)
//        } ?: run { null }
//    }

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