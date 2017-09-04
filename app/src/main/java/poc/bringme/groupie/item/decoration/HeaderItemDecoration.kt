package poc.bringme.groupie.item.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View

class HeaderItemDecoration(
        private val gutter: Int,
        @param:LayoutRes private val headerViewType: Int,
        @ColorInt bgColor: Int = 0) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint()

    init {
        paint.color = bgColor
    }

    fun isHeader(child: View, parent: RecyclerView): Boolean {
        val viewType = parent.layoutManager.getItemViewType(child)
        return viewType == headerViewType
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (!isHeader(view, parent)) return

        outRect.left = gutter
        outRect.right = gutter
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        for (i in 0..parent.childCount - 1) {
            val child = parent.getChildAt(i)
            if (!isHeader(child, parent)) continue

            val lm = parent.layoutManager

            val right = lm.getDecoratedRight(child) + child.translationX
            val left = lm.getDecoratedLeft(child) + child.translationX
            val top = lm.getDecoratedTop(child) + child.translationY
            var bottom = lm.getDecoratedBottom(child) + child.translationY
            if (i == parent.childCount - 1) {
                bottom = Math.max(parent.height.toFloat(), bottom)
            }

            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
