package com.example.testapplication.extension

import android.content.Context
import com.example.testapplication.R

fun Context.somethingWentWrong(): String {
    return resources.getString(R.string.something_went_wrong)
}