package ashutosh.shopit.models

data class CartProductsResponse(
    val content: List<CartContent>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)