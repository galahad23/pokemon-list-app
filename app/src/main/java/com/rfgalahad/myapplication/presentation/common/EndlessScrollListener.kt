package com.rfgalahad.myapplication.presentation.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
            firstVisibleItemPosition >= 0
        ) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}