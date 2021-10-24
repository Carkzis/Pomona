package com.carkzis.pomona.util

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carkzis.pomona.ui.DomainFruit
import com.carkzis.pomona.ui.list.FruitListAdapter
import timber.log.Timber

/**
 * Binds fruit list data to the RecyclerView in the FruitListFragment.
 */
@BindingAdapter("app:fruitListData")
fun bindFruitToRecyclerView(recyclerView: RecyclerView, data: List<DomainFruit>?) {
    val adapter = recyclerView.adapter as FruitListAdapter
    adapter.submitList(data)
}

/**
 * Binds the loading state of the FruitAPI network call to the ProgressBar
 * in the FruitDetailFragment.
 */
@BindingAdapter("loadingState")
fun bindLoadingState(statusProgressBar: ProgressBar, loadingState: LoadingState?) {
    when (loadingState) {
        is LoadingState.Loading ->
            statusProgressBar.visibility = View.VISIBLE
        else ->
            statusProgressBar.visibility = View.GONE
    }
}