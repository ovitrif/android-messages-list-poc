package poc.bringme.groupie.interactor

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import poc.bringme.groupie.core.MyExpandableGroup
import poc.bringme.groupie.item.MessageGroupHeader
import poc.bringme.groupie.item.MessageItem

class GroupCallbacks(private val callback: SwipeCallback) {

    fun swipe(adapter: GroupAdapter<ViewHolder>) = object : SwipeTouchCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = adapter.getItem(viewHolder.adapterPosition)

            callback.onSwipeRemove()

            when (item) {
                is MessageGroupHeader -> {
                    adapter.remove(adapter.getGroup(item))
                    Log.d("DEBUG", item.toString())
                }
                is MessageItem -> {
                    val parent = item.getParent() as MyExpandableGroup
                    parent.remove(item)
                    Log.d("DEBUG", item.toString())
                }
                else -> Log.d("DEBUG", item.toString())
            }
        }
    }

    interface SwipeCallback {
        fun onSwipeRemove()
    }
}
