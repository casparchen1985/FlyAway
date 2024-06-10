package com.caspar.flyaway.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.caspar.flyaway.tool.DimensionCalculator.Companion.toPX

class FlightDecoration : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(10.toPX, 5.toPX, 10.toPX, 5.toPX)
    }
}