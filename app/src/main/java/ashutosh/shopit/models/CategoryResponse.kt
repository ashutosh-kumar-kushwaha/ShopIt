package ashutosh.shopit.models

data class CategoryResponse(
    val content: List<CategoryContent>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)