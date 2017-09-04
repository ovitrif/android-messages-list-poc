package poc.bringme.groupie.item.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.xwray.groupie.ViewHolder

class InsetItemDecoration(
        @param:Dimension private val gutter: Int,
        private val key: String,
        private val value: String,
        @ColorInt bgColor: Int = 0) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint()

    init {
        paint.color = bgColor
    }

    private fun isInset(view: View, parent: RecyclerView): Boolean {
        val viewHolder = parent.getChildViewHolder(view) as ViewHolder
        if (viewHolder.extras.containsKey(key)) {
            return viewHolder.extras[key] == value
        } else {
            return false
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (!isInset(view, parent)) return

        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val gridLayoutManager = parent.layoutManager as GridLayoutManager
        val spanSize = layoutParams.spanSize.toFloat()
        val totalSpanSize = gridLayoutManager.spanCount.toFloat()

        val n = totalSpanSize / spanSize // num columns
        val c = layoutParams.spanIndex / spanSize // column index

        val leftPadding = gutter * ((n - c) / n)
        val rightPadding = gutter * ((c + 1) / n)

        outRect.left = leftPadding.toInt()
        outRect.right = rightPadding.toInt()
        outRect.bottom = gutter
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val lm = parent.layoutManager

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            if (!isInset(child, parent)) continue

            //
            if (child.translationX != 0f || child.translationY != 0f) {
                c.drawRect(
                        lm.getDecoratedLeft(child).toFloat(),
                        lm.getDecoratedTop(child).toFloat(),
                        lm.getDecoratedRight(child).toFloat(),
                        lm.getDecoratedBottom(child).toFloat(),
                        paint
                )
                continue
            }

            val isLast = i == childCount - 1
            val top = child.top + child.translationY
            val bottom = child.bottom + child.translationY

            // Left border
            c.drawRect(
                    lm.getDecoratedLeft(child) + child.translationX,
                    top,
                    child.left + child.translationX,
                    bottom,
                    paint)

            var right = lm.getDecoratedRight(child) + child.translationX
            if (isLast) {
                right = Math.max(right, parent.width.toFloat())
            }

            // Right border
            c.drawRect(
                    child.right + child.translationX,
                    top,
                    right,
                    bottom,
                    paint)

            // Bottom border
            c.drawRect(
                    lm.getDecoratedLeft(child) + child.translationY,
                    bottom,
                    right,
                    lm.getDecoratedBottom(child) + child.translationY,
                    paint)
        }
    }
}
