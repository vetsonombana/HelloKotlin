package repositories


import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Product
import model.ProductsResponse
import okhttp3.OkHttpClient
import okhttp3.Request

class ProductRepository {

    private val client = OkHttpClient()

    suspend fun fetchProducts(): List<Product> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://dummyjson.com/products")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return@withContext emptyList<Product>()

            val body = response.body?.string() ?: return@withContext emptyList<Product>()
            val productsResponse = Gson().fromJson(body, ProductsResponse::class.java)
            return@withContext productsResponse.products
        }
    }
}