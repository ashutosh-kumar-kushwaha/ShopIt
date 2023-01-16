package ashutosh.shopit.models

data class CartItem(
    val id: Int,
    val productName: String,
    val stockDetails: String,
    val price: Int,
    val productImage: Int
)