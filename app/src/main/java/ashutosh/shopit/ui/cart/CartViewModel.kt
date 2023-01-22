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

    fun getCartProducts(){
        viewModelScope.launch {
            cartRepository.getCartProducts()
        }
    }

}