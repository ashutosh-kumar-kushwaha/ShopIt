package ashutosh.shopit.models

data class ProductsResponse(
    val content: List<ProductsContent>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)