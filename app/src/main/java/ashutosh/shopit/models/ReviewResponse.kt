package ashutosh.shopit.models

data class ReviewResponse(
    val content: List<Review>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)