package ashutosh.shopit.models

data class SignUpRequest(
    val email: String,
    val one_time_password: String,
    val firstname: String,
    val lastname: String,
    val gender: String,
    val password: String
)