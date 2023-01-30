package ashutosh.shopit.models

data class MyOrderResponse(
    val myOrderContent: List<MyOrderContent>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)