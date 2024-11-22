package com.nexters.fullstack.ui.widget.bottomsheet.recyclerview

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.nexters.fullstack.R
import com.nexters.fullstack.util.extension.toPx

class GridLayoutRecyclerOnScrollListener(
    private val toolbar: CollapsingToolbarLayout,
    private val frameLayout: FrameLayout,
    private val title: TextView
) :
    RecyclerView.OnScrollListener() {

    private val minSize = 4.toPx.toFloat()
    private val maxSize = 7.toPx.toFloat()
    private val OPACITY_ZERO = 0
    private val OPACITY_ONE = 1f

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as? GridLayoutManager

        val visibleItemPosition = layoutManager?.findFirstCompletelyVisibleItemPosition() ?: 999

        if (visibleItemPosition != FIRST_POSITION && visibleItemPosition % 2 == 0) {
            toolbar.background.alpha = OPACITY_ZERO
            title.apply {
                gravity = Gravity.CENTER
                textSize = minSize
            }
            frameLayout.visibility = View.GONE
        } else if (recyclerView.canScrollVertically(-1)) {
            toolbar.setBackgroundColor(
                ResourcesCompat.getColor(
                    recyclerView.resources,
                    R.color.background,
                    null
                )
            )
            toolbar.alpha = OPACITY_ONE
            title.apply {
                gravity = Gravity.START
                textSize = maxSize
            }
            frameLayout.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val FIRST_POSITION = 0
    }
}