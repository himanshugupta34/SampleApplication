package com.example.testapplication.repository

import android.content.Context
import com.example.testapplication.models.PageData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl : IDashboardRepository {

    override suspend fun loadJsonData(
        context: Context,
        pageNum: Int
    ): PageData? = withContext(Dispatchers.IO) {
        val fileName = getFileName(pageNum)
        return@withContext try {
            val inputStream: InputStream? = context.assets?.open(fileName)
            val size: Int? = inputStream?.available()
            val buffer = size?.let { ByteArray(it) }
            inputStream?.read(buffer)
            inputStream?.close()
            val jsonString = buffer?.let { String(it, Charset.defaultCharset()) }
            jsonString?.let { Gson().fromJson(it, PageData::class.java) } ?: run { null }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileName(pageNum: Int): String {
        return when (pageNum) {
            1 -> "page_one.json"
            2 -> "page_two.json"
            3 -> "page_three.json"
            else -> "invalid"
        }
    }
}