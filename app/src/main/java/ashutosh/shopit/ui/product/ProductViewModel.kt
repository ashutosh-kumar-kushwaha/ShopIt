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

    val productDetailsResponse : SingleLiveEvent<NetworkResult<ProductDetailsResponse>> get() = productRepository.productDetailsResponse

    suspend fun getProductDetails(productId: Int){
        viewModelScope.launch {
            productRepository.getProductDetails(productId)
        }
    }
}