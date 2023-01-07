package ashutosh.shopit.models

data class ProductsContent(
    val productId: Int,
    val image: Image,
    val productName: String,
    val originalPrice: Double,
    val offerPercentage: Double,
    val rating: Any?,
    val noOfOrders: Int
)