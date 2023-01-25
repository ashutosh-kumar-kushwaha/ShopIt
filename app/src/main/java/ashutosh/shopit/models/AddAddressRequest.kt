package ashutosh.shopit.models

import com.google.gson.annotations.SerializedName

data class AddAddressRequest(
    val type: String,
    val name: String,
    val mobile: String,
    @SerializedName("pincode") val pinCode: String,
    val locality: String,
    val addressLine: String,
    val city: String,
    val state: String,
    var landmark: String?,
    @SerializedName("mobile_alternative") var mobileAlternative: String?,
)