package com.javdc.napptiloompas.presentation.ui.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val positionsToLoadMore: Int = 8,
    private val loadMore: () -> Unit,
) : RecyclerView.OnScrollListener() {

    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!isLoading && dy > 0) {
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            val lastPosition = layoutManager.itemCount - 1
            if (lastVisiblePosition > lastPosition - positionsToLoadMore) {
                isLoading = true
                loadMore()
            }
        }
    }

}