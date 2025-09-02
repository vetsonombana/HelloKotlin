package com.example.hellokotlin

import model.Product

object FavoritesManager {
    private val favorites = mutableListOf<Product>()

    fun addFavorite(product: Product) {
        if (!favorites.any { it.id == product.id }) {
            favorites.add(product)
        }
    }

    fun removeFavorite(product: Product) {
        favorites.removeAll { it.id == product.id }
    }

    fun isFavorite(product: Product): Boolean {
        return favorites.any { it.id == product.id }
    }

    fun getFavorites(): List<Product> {
        return favorites
    }
}