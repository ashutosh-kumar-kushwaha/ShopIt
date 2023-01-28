package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.AddReviewResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addReviewResponse = SingleLiveEvent<NetworkResult<AddReviewResponse>>()

    suspend fun addReview(productId: Int, images: List<MultipartBody.Part>, review: MultipartBody.Part){
        addReviewResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.addReview(productId, images, review)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        addReviewResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        addReviewResponse.value = NetworkResult.Error(200, "Something went wrong\nResponse is null")
                    }
                }
                else -> {
                    addReviewResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addReviewResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}