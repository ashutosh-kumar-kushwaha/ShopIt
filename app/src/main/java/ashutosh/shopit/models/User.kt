package ashutosh.shopit.models

data class User(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val profilePhoto: String
)