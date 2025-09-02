package com.example.fragment

import adapter.ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Product
import repositories.ProductRepository


class ProductsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val repository = ProductRepository()
    private lateinit var progressBar: ProgressBar
    private val favorites = mutableListOf<Product>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerView = view.findViewById(R.id.recyclerProducts)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadProducts()
        return view
    }

    private fun loadProducts() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        CoroutineScope(Dispatchers.Main).launch {
            val products = repository.fetchProducts()

            recyclerView.adapter = ProductAdapter(products,
                onItemClick = { product ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailFragment.newInstance(product))
                        .addToBackStack(null)
                        .commit()
                },
                onFavoriteClick = { product ->
                    if (!favorites.contains(product)) {
                        favorites.add(product)
                    } else {
                        favorites.remove(product)
                    }
                }
            )
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}