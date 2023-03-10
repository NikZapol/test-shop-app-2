package com.example.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.home.databinding.ItemLatestRvBinding
import com.example.home.databinding.ItemSaleRvBinding
import com.example.home.models.Latest
import com.example.home.models.Sale
import java.text.DecimalFormat

class SaleAdapter: RecyclerView.Adapter<SaleAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemSaleRvBinding):
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Sale>(){
        override fun areItemsTheSame(oldItem: Sale, newItem: Sale): Boolean {
            return oldItem.name == newItem.name

        }

        override fun areContentsTheSame(oldItem: Sale, newItem: Sale): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var saleList: List<Sale>

    init {
        saleList = emptyList()
        differ.submitList(saleList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSaleRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSale = saleList[position]

        holder.binding.apply {
            nameTextView.text = currentSale.name
            categoryTextView.text = currentSale.category
            discountTextView.text = "${currentSale.discount}% off"
            val df = DecimalFormat("#,##0.00")
            val formattedPrice = df.format(currentSale.price)
            priceTextView.text = "$ $formattedPrice"

            imageView.load(currentSale.image_url) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount()= saleList.size
}
