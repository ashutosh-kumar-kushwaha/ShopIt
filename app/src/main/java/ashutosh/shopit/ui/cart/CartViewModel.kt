package ashutosh.shopit.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.CartProductsResponse
import ashutosh.shopit.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    val getCartProductsResponse get() = cartRepository.getCartProductsResponse
    val changeProductQuantity get() = cartRepository.changeProductQuantity

    fun getCartProducts(){
        viewModelScope.launch {
            cartRepository.getCartProducts()
        }
    }

    fun increaseProductQuantity(productId: Int){
        viewModelScope.launch {
            cartRepository.increaseProductQuantity(productId)
        }
    }

    fun decreaseProductQuantity(productId: Int){
        viewModelScope.launch {
            cartRepository.decreaseProductQuantity(productId)
        }
    }

}