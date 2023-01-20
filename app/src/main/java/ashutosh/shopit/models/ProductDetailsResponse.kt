package ashutosh.shopit.models

data class ProductDetailsResponse(
    val productId: Int,
    val productName: String,
    val imageUrls: List<Image>,
    val originalPrice: Double,
    val offerPercentage: Double,
    val rating: Any?,
    val quantityAvailable: Int,
    val warranty: String,
    val offers: List<Offer>,
    val services: String,
    val specification: List<Specification>,
    val description: List<Description>,
    val category: List<Category>,
    val provider: Provider,
    val highlights: String,
    val noOfOrders: Int
)