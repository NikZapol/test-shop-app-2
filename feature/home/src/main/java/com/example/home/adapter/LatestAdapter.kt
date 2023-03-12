package com.example.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.home.databinding.ItemLatestRvBinding
import com.example.home.models.Latest

class LatestAdapter: RecyclerView.Adapter<LatestAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemLatestRvBinding):
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Latest>(){
        override fun areItemsTheSame(oldItem: Latest, newItem: Latest): Boolean {
            return oldItem.name == newItem.name

        }

        override fun areContentsTheSame(oldItem: Latest, newItem: Latest): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var latEst:List<Latest>
    get() = differ.currentList
    set(value) {
        differ.submitList(value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLatestRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLatest = latEst[position]

        holder.binding.apply {
            nameTextView.text = currentLatest.name
            categoryTextView.text = currentLatest.category

            val price = currentLatest.price
            val formattedPrice = String.format("$ %,d", price)
            priceTextView.text = formattedPrice

            imageView.load(currentLatest.image_url) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount()= latEst.size
}
