package com.example.hellokotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Produits"

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Fragment par défaut
        loadFragment(ProductsFragment())
        toolbar.title = "Produits"

        // Écouter les changements de fragment pour afficher le bouton retour
        supportFragmentManager.addOnBackStackChangedListener {
            val canGoBack = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
        }

        // Gestion des clics sur la BottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_products -> {
                    loadFragment(ProductsFragment())
                    toolbar.title = "Produits"
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    toolbar.title = "Recherche"
                    true
                }
                R.id.nav_favorites -> {
                     loadFragment(FavoritesFragment())
                     toolbar.title = "Favoris"
                    true
                }
                R.id.nav_cart -> {
                    loadFragment(CartFragment())
                    toolbar.title = "Panier"
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Gestion du clic sur le bouton retour
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}