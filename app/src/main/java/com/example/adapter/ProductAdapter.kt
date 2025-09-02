package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.manager.FavoritesManager
import com.example.fragment.R
import model.Product

class ProductAdapter(private val products: List<Product>, private val onItemClick : (Product) -> Unit, private val onFavoriteClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.productImage)
        val title: TextView = view.findViewById(R.id.productTitle)
        val description: TextView = view.findViewById(R.id.productDescription)
        val price: TextView = view.findViewById(R.id.productPrice)
        val favorite: ImageButton = view.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.title.text = product.title
        holder.description.text = product.description
        holder.price.text = "$${product.price}"
        Glide.with(holder.image.context).load(product.thumbnail).into(holder.image)

        // état favoris
        if (FavoritesManager.isFavorite(product)) {
            holder.favorite.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorite_border)
        }

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }

        holder.favorite.setOnClickListener {
            if (FavoritesManager.isFavorite(product)) {
                FavoritesManager.removeFavorite(product)
                holder.favorite.setImageResource(R.drawable.ic_favorite_border)
            } else {
                FavoritesManager.addFavorite(product)
                holder.favorite.setImageResource(R.drawable.ic_favorite_filled)
            }
            notifyItemChanged(position)
            onFavoriteClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}