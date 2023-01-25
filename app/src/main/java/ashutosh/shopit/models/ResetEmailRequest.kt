package ashutosh.shopit.models

import com.google.gson.annotations.SerializedName

data class ResetEmailRequest(
    val email: String,
    @SerializedName("one_time_password") val oneTimePassword: String
)