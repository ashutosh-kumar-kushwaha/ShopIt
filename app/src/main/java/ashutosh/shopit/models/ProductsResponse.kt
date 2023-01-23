package ashutosh.shopit.models

data class ProductsResponse(
    val content: List<Product>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)