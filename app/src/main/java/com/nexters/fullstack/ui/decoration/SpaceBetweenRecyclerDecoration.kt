package com.nexters.fullstack.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.extension.toPx

class SpaceBetweenRecyclerDecoration(private val vertical: Int = 0, private val horizontal: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = vertical.toPx
        outRect.top = vertical.toPx
        outRect.right = horizontal.toPx
        outRect.left = horizontal.toPx
    }
}