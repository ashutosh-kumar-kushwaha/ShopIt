package ashutosh.shopit.models

data class ChangeProfilePicResponse(
    val profilePhoto: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val gender: String,
    val phoneNumber: Any?,
    val id: Int
)