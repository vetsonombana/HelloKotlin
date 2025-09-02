package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapter.ProductAdapter
import com.example.manager.FavoritesManager
import model.Product

class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById(R.id.recyclerFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val favoriteProducts = FavoritesManager.getFavorites()
        adapter = ProductAdapter(favoriteProducts.toMutableList(),
            onItemClick = { product ->
            // Exemple : afficher un toast ou aller vers le détail
            // findNavController().navigate(R.id.action_favorites_to_details, bundleOf("productId" to product.id))
        },
            onFavoriteClick = { product ->
                // Ici tu peux mettre à jour la liste si l’utilisateur supprime un favori
                val updatedFavorites = FavoritesManager.getFavorites().toMutableList()
                adapter = ProductAdapter(updatedFavorites, this::onProductClick, this::onFavoriteClick)
                recyclerView.adapter = adapter
            })
        recyclerView.adapter = adapter

        return view
    }

    private fun onProductClick(product: Product) {
        // Aller sur le détail
        // findNavController().navigate(...)
    }

    private fun onFavoriteClick(product: Product) {
        // Refresh si on retire un favori
        val updatedFavorites = FavoritesManager.getFavorites()
        adapter = ProductAdapter(updatedFavorites, this::onProductClick, this::onFavoriteClick)
        recyclerView.adapter = adapter
    }
}