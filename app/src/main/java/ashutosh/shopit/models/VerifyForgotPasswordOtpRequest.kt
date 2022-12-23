package ashutosh.shopit.models

data class VerifyForgotPasswordOtpRequest(
    val email: String,
    val oneTimePassword: String
)