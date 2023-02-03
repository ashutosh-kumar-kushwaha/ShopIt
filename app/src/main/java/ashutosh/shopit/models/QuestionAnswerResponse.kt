package ashutosh.shopit.models

data class QuestionAnswerResponse(
    val content: List<Question>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPage: Int,
    val totalElements: Int,
    val lastPage: Boolean
)