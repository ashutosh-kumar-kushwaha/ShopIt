package ashutosh.shopit.models

data class MyOrderResponse(
    val content: List<MyOrderContent>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)