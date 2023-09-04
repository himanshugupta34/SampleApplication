package com.example.testapplication.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstRowDecorator(
    private val firstRowMargin: Float,
    private val defaultMargin: Float
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        outRect.top = if (position < (parent.layoutManager as GridLayoutManager).spanCount)
            firstRowMargin.toInt() else defaultMargin.toInt()
    }
}
