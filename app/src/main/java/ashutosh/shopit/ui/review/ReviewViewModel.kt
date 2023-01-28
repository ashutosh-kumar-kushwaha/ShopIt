package ashutosh.shopit.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val reviewRepository: ReviewRepository)  : ViewModel() {

    var productId = -1
    var productName = ""
    var productImage = ""

    val addReviewResponse get() =  reviewRepository.addReviewResponse

    fun addReview(images: List<MultipartBody.Part>, review: MultipartBody.Part){
        viewModelScope.launch {
            reviewRepository.addReview(productId, images, review)
        }
    }
}