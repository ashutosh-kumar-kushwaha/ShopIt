package ashutosh.shopit.models

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val firstname: String,
    val lastname: String,
    val roles: List<Role>
)