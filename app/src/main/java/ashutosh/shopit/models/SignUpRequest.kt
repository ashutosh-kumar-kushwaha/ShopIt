package ashutosh.shopit.models

data class SignUpRequest(
    val one_time_password: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val gender: String,
    val password: String
)