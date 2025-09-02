package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import model.Product

class ProductDetailFragment : Fragment() {
    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_PRODUCT, product) // Product doit implémenter Parcelable
            fragment.arguments = bundle
            return fragment
        }
    }

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable(ARG_PRODUCT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Modifier le titre de la toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = product?.title ?: "Détails du produit"

        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)

        val image: ImageView = view.findViewById(R.id.detailImage)
        val title: TextView = view.findViewById(R.id.detailTitle)
        val description: TextView = view.findViewById(R.id.detailDescription)
        val price: TextView = view.findViewById(R.id.detailPrice)

        product?.let {
            title.text = it.title
            description.text = it.description
            price.text = "$${it.price}"
            Glide.with(view.context).load(it.thumbnail).into(image)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Produits"
    }
}