package com.example.testapplication.repository

import android.content.Context
import com.example.testapplication.models.PageData

interface IDashboardRepository {

    suspend fun loadJsonData(context: Context, pageNum: Int): PageData?
}