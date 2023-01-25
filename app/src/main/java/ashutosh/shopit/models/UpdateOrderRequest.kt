package ashutosh.shopit.models

import com.google.gson.annotations.SerializedName

data class UpdateOrderRequest(
    @SerializedName("razorpay_payment_id") val razorpayPaymentId: String,
    @SerializedName("razorpay_order_id") val razorpayOrderId: String,
    @SerializedName("razorpay_signature") val razorpaySignature: String
)