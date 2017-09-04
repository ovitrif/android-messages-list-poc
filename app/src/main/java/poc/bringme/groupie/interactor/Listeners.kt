package poc.bringme.groupie.interactor

import android.content.Context
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import poc.bringme.groupie.PAGE_COUNT
import poc.bringme.groupie.data.Data
import poc.bringme.groupie.item.MessageItem
import poc.bringme.groupie.utils.createRandomText

class Listeners(
        private val context: Context,
        private val adapter: GroupAdapter<ViewHolder>,
        private val layoutManager: GridLayoutManager,
        private val data: Data) {

    var index = 13

    val onItemClick = OnItemClickListener { item, view ->
        Toast.makeText(context, "Item ${item.id} Clicked", Toast.LENGTH_SHORT).show()
    }

    fun onBottomReached() = object : InfiniteScrollListener(layoutManager) {
        override fun onLoadMore(currentPage: Int) {

            val runnable = {
                if (currentPage <= PAGE_COUNT) {
                    adapter.remove(data.loader)

                    adapter.add(Section().apply {
                        for (i in 1..5) {
                            add(MessageItem(
                                    title = "Page $currentPage Message $i",
                                    timestamp = "${index++} days ago",
                                    body = createRandomText()))
                        }
                    })

                    if (currentPage < PAGE_COUNT) {
                        adapter.add(data.loader)
                    }
                }
            }

            // Simulate async loading
            Handler().postDelayed(runnable, 1000)
        }
    }
}
