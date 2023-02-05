package ashutosh.shopit.models

data class Content(
    val orderId: String,
    val image: String?,
    val dateOfPurchase: Any?,
    val createdAt: String,
    val deliveryStatus: List<Any>,
    val amount: Int,
    val paymentId: String?,
    val status: String
)