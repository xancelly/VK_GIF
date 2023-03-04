package com.example.vkgif.presentation.fragments.search_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkgif.R
import com.example.vkgif.domain.models.Data

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(searchView: View): RecyclerView.ViewHolder(searchView) {
        val gifImageView: ImageView = searchView.findViewById(R.id.gifImageView)
    }

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = gifList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentData = gifList[position]
        Glide.with(holder.itemView.context)
            .load(currentData.images.preview_gif.url)
            .into(holder.gifImageView)
    }
}