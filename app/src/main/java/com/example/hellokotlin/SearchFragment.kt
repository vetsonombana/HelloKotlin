package com.example.hellokotlin

import adapter.ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repositories.ProductRepository
import model.Product

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val repository = ProductRepository()
    private var allProducts: List<Product> = listOf()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerSearch)
        searchView = view.findViewById(R.id.searchView)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadProducts()
        setupSearch()

        return view
    }

    private fun loadProducts() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        CoroutineScope(Dispatchers.Main).launch {
            allProducts = repository.fetchProducts()
            recyclerView.adapter = ProductAdapter(allProducts,
                onItemClick = { product ->
                    // ouvrir le fragment détail
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailFragment.newInstance(product))
                        .addToBackStack(null)
                        .commit()
                },
                onFavoriteClick = { product ->
                    // Gérer le clic sur favoris (ajout ou retrait)
                    // Ici on ne recharge pas la liste, car c’est géré par l’adapter
                })
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = allProducts.filter {
                    it.title.contains(newText ?: "", ignoreCase = true)
                }
                recyclerView.adapter = ProductAdapter(filtered,
                    onItemClick = { product ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailFragment.newInstance(product))
                        .addToBackStack(null)
                        .commit()
                },
                    onFavoriteClick = { product ->
                        // Gérer l’ajout/retrait des favoris ici
                    }
                )
                return true
            }
        })
    }
}