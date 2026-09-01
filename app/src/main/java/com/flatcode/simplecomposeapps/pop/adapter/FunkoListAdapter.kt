package com.flatcode.simplecomposeapps.pop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.databinding.ItemPopBinding
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.utils.DATA

class FunkoListAdapter : ListAdapter<PopItem, FunkoListAdapter.PopViewHolder>(PopDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopViewHolder {
        val binding = ItemPopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PopViewHolder(private val binding: ItemPopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopItem) {
            binding.popName.text = item.name
            binding.imageView.load(DATA.IMAGE_POP) {
                crossfade(true)
                placeholder(R.color.image_profile)
                error(R.color.image_profile)
            }
        }
    }

    companion object PopDiffCallback : DiffUtil.ItemCallback<PopItem>() {
        override fun areItemsTheSame(oldItem: PopItem, newItem: PopItem): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PopItem, newItem: PopItem): Boolean =
            oldItem == newItem
    }
}