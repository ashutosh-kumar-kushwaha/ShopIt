package ashutosh.shopit.models

data class CartContent(
    val product: Product,
    val dateOfOrder: String,
    val noOfProducts: Int,
    val available: Boolean,
    val id: Int
)