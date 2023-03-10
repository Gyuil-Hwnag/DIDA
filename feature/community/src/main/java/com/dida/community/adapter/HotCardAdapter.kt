package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.HotCardActionHandler
import com.dida.community.R
import com.dida.community.databinding.HolderHotCardBinding
import com.dida.domain.model.nav.community.HotCard

class HotCardAdapter(
    private val eventListener: HotCardActionHandler
) : ListAdapter<HotCard, HotCardAdapter.ViewHolder>(ActivieNftDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hot_card,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).cardId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_hot_card

    class ViewHolder(private val binding: HolderHotCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HotCard) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object ActivieNftDiffCallback : DiffUtil.ItemCallback<HotCard>() {
        override fun areItemsTheSame(oldItem: HotCard, newItem: HotCard) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HotCard, newItem: HotCard) =
            oldItem == newItem
    }
}
