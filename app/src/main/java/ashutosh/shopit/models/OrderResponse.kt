package ashutosh.shopit.models

data class OrderResponse(
    val amount: Int,
    val amountPaid: Int,
    val notes: List<Any>,
    val createdAt: Int,
    val amountDue: Int,
    val currency: String,
    val receipt: String,
    val id: String,
    val entity: String,
    val offerId: Any?,
    val status: String,
    val attempts: Int
)