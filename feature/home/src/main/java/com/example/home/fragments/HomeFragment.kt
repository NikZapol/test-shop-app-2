package com.example.home.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.R
import com.example.home.adapter.LatestAdapter
import com.example.home.adapter.SaleAdapter
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.viewmodel.LatestViewModel
import com.example.home.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModelLatest: LatestViewModel by viewModels()
    private val viewModelSale: SaleViewModel by viewModels()
    private lateinit var latestAdapter: LatestAdapter
    private lateinit var saleAdapter: SaleAdapter
    private lateinit var categoryRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionBarView = inflater.inflate(R.layout.action_bar_page1, null)
        actionBar?.customView = actionBarView

        setUpRv()
        initCategoryRecyclerView()
    }

    private fun initCategoryRecyclerView() {
        categoryRv = binding.categoryRv
        categoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRv.adapter = CategoryAdapter()
    }

    private fun setUpRv() {
        binding.latestRv.visibility = View.GONE
        binding.rvSale.visibility = View.GONE

        viewModelLatest.responseLatest.observe(viewLifecycleOwner, { listLatest ->
            if (!::latestAdapter.isInitialized){
                latestAdapter=LatestAdapter()
            }
            latestAdapter.latEst = listLatest
            checkDataAvailability()
        })

        viewModelSale.responseSale.observe(viewLifecycleOwner, { listSale ->
            if (!::saleAdapter.isInitialized) {  // check if saleAdapter has been initialized
                saleAdapter = SaleAdapter()  // initialize saleAdapter
            }
            saleAdapter.saleList = listSale
            checkDataAvailability()
        })
    }

    private fun checkDataAvailability() {
        if (viewModelLatest.responseLatest.value != null && viewModelSale.responseSale.value != null) {
            binding.latestRv.apply {
                visibility = View.VISIBLE
                adapter = latestAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }

            binding.rvSale.apply {
                visibility = View.VISIBLE
                adapter = saleAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }

            binding.rvBrands.apply {
                adapter = latestAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }
        }
    }
    class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private val categories = listOf(
            Category("phones", R.drawable.phone_category_vector),
            Category("headphones", R.drawable.headphones_category_vector),
            Category("games", R.drawable.games_category_vector),
            Category("cars", R.drawable.cars_category_vector),
            Category("furniture", R.drawable.furniture_category_vector),
            Category("kids", R.drawable.kids_category_vector)
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.bind(categories[position])
        }

        override fun getItemCount() = categories.size

        class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            private val categoryIcon = itemView.findViewById<ImageView>(R.id.category_icon)
            private val categoryName = itemView.findViewById<TextView>(R.id.category_name)

            fun bind(category: Category) {
                categoryIcon.setImageResource(category.iconRes)
                categoryName.text = category.name
            }

        }

        data class Category(val name: String, @DrawableRes val iconRes: Int)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
