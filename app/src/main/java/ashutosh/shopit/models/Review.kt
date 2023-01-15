package ashutosh.shopit.models

data class Review(
    val name: String,
    val message: String,
    val images: List<Image>
)