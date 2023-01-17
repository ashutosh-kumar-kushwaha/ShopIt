package ashutosh.shopit.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.ProductDetailsResponse
import ashutosh.shopit.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    var productId = -1

    val productDetailsResponse : SingleLiveEvent<NetworkResult<ProductDetailsResponse>> get() = productRepository.productDetailsResponse

    fun getProductDetails(){
        viewModelScope.launch {
            productRepository.getProductDetails(productId)
        }
    }
}