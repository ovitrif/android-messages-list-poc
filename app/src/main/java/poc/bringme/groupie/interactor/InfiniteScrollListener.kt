package poc.bringme.groupie.interactor

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class InfiniteScrollListener(
        private var linearLayoutManager: LinearLayoutManager?) :RecyclerView.OnScrollListener() {

    private val visibleThreshold = 1 // The minimum amount of items to have below your current scroll position before isLoading more.

    private var isLoading = true
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var currentPage = 0

    fun reset() {
        isLoading = true
        previousTotal = 0
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        currentPage = 0
    }

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView!!.childCount
        totalItemCount = linearLayoutManager!!.itemCount
        firstVisibleItem = linearLayoutManager!!.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal || totalItemCount == 0) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }

        // End has been reached
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            currentPage++
            recyclerView.post({ onLoadMore(currentPage) })
            isLoading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)
}
