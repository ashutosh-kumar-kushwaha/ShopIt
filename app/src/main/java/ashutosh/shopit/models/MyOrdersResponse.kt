package ashutosh.shopit.models

data class MyOrdersResponse(
    val content: List<Content>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)