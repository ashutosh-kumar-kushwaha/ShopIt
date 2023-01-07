package ashutosh.shopit.models

data class ProductsContent(
    val productId: Int,
    val imageUrls: Image,
    val productName: String,
    val originalPrice: Double,
    val offerPercentage: Double,
    val rating: Double?,
    val noOfOrders: Int
)