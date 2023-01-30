package ashutosh.shopit.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.ProductDetailsResponse
import ashutosh.shopit.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    var productId = -1

    val productDetailsResponse : SingleLiveEvent<NetworkResult<ProductDetailsResponse>> get() = productRepository.productDetailsResponse
    val addToCartResponse : SingleLiveEvent<NetworkResult<DefaultResponse>> get() = productRepository.addToCartResponse
    val reviewResponse get() = productRepository.reviewResponse
    var currentPrice = 0

    val toastMsg = SingleLiveEvent<String>()

    val quantity = MutableLiveData("1")

    fun getProductDetails(){
        viewModelScope.launch {
            productRepository.getProductDetails(productId)
        }
    }

    fun decreaseQuantity(){
        val q = Integer.parseInt(quantity.value!!)
        if(q >= 1){
            quantity.value = (q - 1).toString()
        }
    }

    fun increaseQuantity(){
        val q = Integer.parseInt(quantity.value!!)
        if(q < 5){
            quantity.value = (q + 1).toString()
        }
        else{
            toastMsg.value = "Maximum Quantity Reached"
        }
    }

    fun addToCart(){
        viewModelScope.launch {
            productRepository.addToCart(productId, Integer.parseInt(quantity.value!!))
        }
    }
}