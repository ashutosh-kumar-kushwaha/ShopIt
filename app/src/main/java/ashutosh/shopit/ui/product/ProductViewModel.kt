package ashutosh.shopit.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.ProductDetailsResponse
import ashutosh.shopit.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    var productId = -1

    val productDetailsResponse : SingleLiveEvent<NetworkResult<ProductDetailsResponse>> get() = productRepository.productDetailsResponse

    fun getProductDetails(){
        viewModelScope.launch {
            productRepository.getProductDetails(productId)
        }
    }
}