package com.carkzis.pomona.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carkzis.pomona.databinding.FruitItemBinding

class FruitListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DomainFruit, FruitListAdapter.FruitListViewHolder>(FruitListDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FruitListViewHolder {
        return FruitListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FruitListViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
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