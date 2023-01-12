package ashutosh.shopit.models

data class Provider(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val phoneNumber: String,
    val profilePhoto: String
)