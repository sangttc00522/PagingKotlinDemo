package com.tech.pagingkotlindemo.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener
/**
 * Supporting only LinearLayoutManager for now.
 */
    (private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        val PAGE_START = 1
        val PAGE_SIZE = 10
    }
}