package poc.bringme.groupie.interactor

import android.support.v7.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder

class Callbacks(private val swipeCallback: SwipeCallback) {

    fun swipe(groupAdapter: GroupAdapter<ViewHolder>, section: Section) = object : SwipeTouchCallback() {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = groupAdapter.getItem(viewHolder.adapterPosition)
            val position = section.getPosition(item)

            try {
                if (section.getItem(position) == item) {
                    section.remove(item)
                    swipeCallback.onSwipeRemove(SwipeRemovePayload(item, section, position))
                }
            } catch (e: IndexOutOfBoundsException) {
            }
        }
    }

    interface SwipeCallback {
        fun onSwipeRemove(payload: SwipeRemovePayload)
    }

    data class SwipeRemovePayload(
            val item: Item<ViewHolder>,
            val section: Section,
            val position: Int)
}
