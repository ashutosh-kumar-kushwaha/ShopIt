package ashutosh.shopit.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val reviewRepository: ReviewRepository)  : ViewModel() {

    var productId = -1
    var productName = ""
    var productImage = ""
    val message= MutableLiveData("")

    val addReviewResponse get() =  reviewRepository.addReviewResponse

    fun addReview(images: List<MultipartBody.Part>, rating: RequestBody, reviewMsg: RequestBody){
        viewModelScope.launch {
            reviewRepository.addReview(productId, images, rating, reviewMsg)
        }
    }
}