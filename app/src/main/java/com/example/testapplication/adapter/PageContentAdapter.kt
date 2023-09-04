package com.example.testapplication.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.RowContentBinding
import com.example.testapplication.models.ContentData

class PageContentAdapter : RecyclerView.Adapter<PageContentAdapter.MyViewHolder>() {

    private var actualPageData: MutableList<ContentData> = mutableListOf()
    private var filteredPageData: MutableList<ContentData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addAllItems(newChunk: MutableList<ContentData>) {
        val oldSize = actualPageData.size
        newChunk.apply {
            actualPageData.addAll(this)
            filteredPageData.clear()
            filteredPageData.addAll(actualPageData)
        }
        notifyItemRangeInserted(oldSize, newChunk.size)
    }

    fun filterItems(query: String) {
        filteredPageData.clear()
        if (query.length >= 3) {
            actualPageData.forEach { contentItem ->
                if (contentItem.name?.contains(query, ignoreCase = true) == true) {
                    val start = contentItem.name.indexOf(query, ignoreCase = true)
                    val end = start + query.length
                    val spannable = SpannableString(contentItem.name)
                    spannable.setSpan(
                        ForegroundColorSpan(Color.YELLOW),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    val copiedItem = contentItem.copy(highlightedName = spannable)
                    filteredPageData.add(copiedItem)
                }
            }
        } else filteredPageData.addAll(actualPageData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredPageData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.pageContentData = filteredPageData[position]
    }

    inner class MyViewHolder(val binding: RowContentBinding) :
        RecyclerView.ViewHolder(binding.root)
}