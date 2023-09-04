package com.example.testapplication.models

import android.text.SpannableString
import com.google.gson.annotations.SerializedName

data class PageData(
    @SerializedName("title")
    val title: String?,
    @SerializedName("total-content-items")
    val totalContentItems: String?,
    @SerializedName("page-num")
    val pageNum: String?,
    @SerializedName("page-size")
    val pageSize: String?,
    @SerializedName("content-items")
    val contentList: MutableList<ContentData>?
)

data class ContentData(
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster-image")
    val posterImage: String?,
    var highlightedName: SpannableString? = null
)
