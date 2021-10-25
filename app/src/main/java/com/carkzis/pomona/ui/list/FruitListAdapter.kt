package com.carkzis.pomona.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carkzis.pomona.R
import com.carkzis.pomona.databinding.FruitItemBinding
import com.carkzis.pomona.ui.DomainFruit
import com.carkzis.pomona.util.getFruitColourFilter

class FruitListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DomainFruit, FruitListAdapter.FruitListViewHolder>(FruitListDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FruitListViewHolder {
        return FruitListViewHolder.from(parent)
    }

    // We require a context in order to get a colour filter to be used on individual fruit layouts.
    private lateinit var context: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: FruitListViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        // We set a specific colour to the itemView based on the fruit.
        holder.itemView.findViewById<View>(R.id.clayout_fruit_item).background.colorFilter =
            getFruitColourFilter(item.type, context)
        holder.bind(item)
    }

    class FruitListViewHolder(private var binding: FruitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: DomainFruit) {
                binding.domainFruit = item
                binding.executePendingBindings()
            }
        companion object {
            fun from(parent: ViewGroup): FruitListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FruitItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return FruitListViewHolder(binding)
            }
        }
    }

    /**
     * This takes the higher-order function [clickListener], which will
     * take the user to the FruitDetailFragment when a fruit in the RecyclerView is clicked.
     */
    class OnClickListener(val clickListener: (fruit: DomainFruit) -> Unit) {
        fun onClick(fruit: DomainFruit) = clickListener(fruit)
    }

}

class FruitListDiffCallBack : DiffUtil.ItemCallback<DomainFruit>() {
    override fun areItemsTheSame(oldItem: DomainFruit, newItem: DomainFruit): Boolean {
        return oldItem.type == newItem.type
    }
    override fun areContentsTheSame(oldItem: DomainFruit, newItem: DomainFruit): Boolean {
        return oldItem == newItem
    }
}