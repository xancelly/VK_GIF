package com.example.vkgif.presentation.fragments.search_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkgif.databinding.GifItemBinding
import com.example.vkgif.domain.models.Data

class SearchAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    /*Интерфейс, определяющий функцию onItemClick,
    которая вызывается при щелчке на элементе списка.*/
    interface OnItemClickListener {
        fun onItemClick(data: Data)
    }
    /*Класс используется для хранения ссылки на представление элемента списка,
    чтобы можно было быстро получать доступ к нему при прокрутке списка.*/
    class SearchViewHolder(val binding: GifItemBinding):
        RecyclerView.ViewHolder(binding.root)

    /*Поле используется для сравнения элементов списка и определения того,
    должен ли RecyclerView обновить элемент списка или нет.*/
    private val diffCallback = object: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    /* Экземпляр AsyncListDiffer, который используется для хранения списка элементов.*/
    private val differ = AsyncListDiffer(this, diffCallback)

    /*Свойство, которое используется для установки и получения списка элементов.*/
    var gifList: List<Data>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    /*Функция, которая вызывается, когда RecyclerView нуждается в новом экземпляре SearchViewHolder.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            GifItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
   /* Функция, которая возвращает количество элементов в списке.*/
    override fun getItemCount(): Int = gifList.size

    /*Функция, которая вызывается, когда RecyclerView хочет отобразить элемент списка в конкретной позиции.
    * Мы получаем текущий элемент списка из gifList, используя позицию, и отображаем его с помощью Glide,
    * затем устанавливаем слушатель кликов на элемент, используя OnItemClickListener.*/
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