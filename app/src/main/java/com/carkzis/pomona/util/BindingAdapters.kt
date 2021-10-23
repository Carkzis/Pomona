package com.carkzis.pomona.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carkzis.pomona.ui.DomainFruit
import com.carkzis.pomona.ui.list.FruitListAdapter
import timber.log.Timber

@BindingAdapter("app:fruitListData")
fun bindFruitToRecyclerView(recyclerView: RecyclerView, data: List<DomainFruit>?) {
    val adapter = recyclerView.adapter as FruitListAdapter
    adapter.submitList(data)
}