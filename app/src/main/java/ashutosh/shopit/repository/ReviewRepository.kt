package ashutosh.shopit.repository

import android.util.Log
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.AddReviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addReviewResponse = SingleLiveEvent<NetworkResult<AddReviewResponse>>()

    suspend fun addReview(productId: Int, images: List<MultipartBody.Part>, rating: RequestBody, reviewMsg: RequestBody){
        addReviewResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.addReview(productId, images, rating, reviewMsg)
            Log.d("Ashu", images.toString())
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        addReviewResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        addReviewResponse.value = NetworkResult.Error(200, response.errorBody().toString())
                    }
                }
                409 -> {
                    addReviewResponse.value = NetworkResult.Error(409, "You can post review only once")
                }
                else -> {
                    addReviewResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addReviewResponse.value = NetworkResult.Error(-1, e.message)
            e.printStackTrace()
        }
    }
}