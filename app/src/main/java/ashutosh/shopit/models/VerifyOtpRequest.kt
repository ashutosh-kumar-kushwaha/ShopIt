package ashutosh.shopit.models

data class VerifyOtpRequest(
    val email: String,
    val oneTimePassword: String
)