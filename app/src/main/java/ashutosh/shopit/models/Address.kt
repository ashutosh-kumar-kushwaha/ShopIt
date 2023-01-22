package ashutosh.shopit.models

import com.google.gson.annotations.SerializedName

data class Address(
    val type: String,
    val name: String,
    val mobile: String,
    @SerializedName("pincode") val pinCode: String,
    val locality: String,
    val addressLine: String,
    val city: String,
    val state: String,
    val landmark: String,
    @SerializedName("mobile_alternative") val mobileAlternative: String,
    val id: Int
)