package ashutosh.shopit.models

import com.google.gson.annotations.SerializedName

data class MyOrderContent(
    val orderId: String,
    val image: String?,
    val dateOfPurchase: Any?,
    @SerializedName("created_at")val createdAt: String,
    val deliveryStatus: List<Any>,
    val amount: Int,
    val paymentId: String?,
    val status: String
)