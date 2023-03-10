package com.example.testapplication.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testapplication.data.preferences.PreferenceHelper

abstract class BaseViewModel(
    private val mPreferenceHelper: PreferenceHelper,
    mApplication: Application
) : AndroidViewModel(mApplication) {

    protected val isExistingUser get() = mPreferenceHelper.getIsExistingUser()
    protected fun clearPreference() = this.mPreferenceHelper.clearData()
}