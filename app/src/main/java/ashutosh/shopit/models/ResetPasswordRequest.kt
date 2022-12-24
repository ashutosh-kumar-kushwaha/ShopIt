package ashutosh.shopit.models

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val password: String
)