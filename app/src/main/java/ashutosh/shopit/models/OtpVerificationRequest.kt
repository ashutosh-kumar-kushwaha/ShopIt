package ashutosh.shopit.models

data class OtpVerificationRequest(
    val email: String,
    val oneTimePassword: String
)