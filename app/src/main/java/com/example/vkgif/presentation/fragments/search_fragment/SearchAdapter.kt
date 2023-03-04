package com.example.vkgif.presentation.fragments.search_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkgif.R
import com.example.vkgif.databinding.FragmentSearchBinding
import com.example.vkgif.databinding.GifItemBinding
import com.example.vkgif.domain.models.Data

class SearchAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(data: Data)
    }
    class SearchViewHolder(val binding: GifItemBinding):
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var gifList: List<Data>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            GifItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = gifList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentData = gifList[position]
        Glide.with(holder.itemView.context)
            .load(currentData.images.preview_gif.url)
            .into(holder.binding.gifImageView)

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(currentData)
        }

        Log.d("SearchAdapter", "Bound item at position $position with data: $currentData")
    }
}