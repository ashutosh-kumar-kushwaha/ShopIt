package ashutosh.shopit.models

data class Product (
    val productId: Int,
    val imageUrls: Image,
    val productName: String,
    val originalPrice: Double,
    val offerPercentage: Double,
    val rating: Int,
    val noOfOrders: Int
)