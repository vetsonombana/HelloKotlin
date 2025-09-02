package com.example.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragment.CartFragment
import com.example.fragment.FavoritesFragment
import com.example.fragment.ProductsFragment
import com.example.fragment.R
import com.example.fragment.SearchFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
//        supportActionBar?.title = "Produits"
        // ⚠️ Initialisation de FirebaseAuth
        auth = FirebaseAuth.getInstance()
        // Afficher l'email de l'utilisateur
        val user = auth.currentUser
        supportActionBar?.title = "Bienvenue, ${user?.email}"

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

    // Charger le menu dans la Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // Gérer le clic sur les éléments du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                Toast.makeText(this, "Déconnecté", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, com.example.activity.LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}