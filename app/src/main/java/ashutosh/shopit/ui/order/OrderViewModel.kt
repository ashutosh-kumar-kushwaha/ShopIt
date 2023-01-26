package ashutosh.shopit.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository: OrderRepository) : ViewModel() {

    val addressResponse get() = orderRepository.addressResponse
    val placeOrderResponse get() = orderRepository.placeOrderResponse
    val getCartProductsResponse get() = orderRepository.getCartProductsResponse
    val deleteAddressResponse get() = orderRepository.deleteAddressResponse

    fun getAddresses(){
        viewModelScope.launch {
            orderRepository.getAddresses()
        }
    }

    fun getCartProducts(){
        viewModelScope.launch {
            orderRepository.getCartProducts()
        }
    }

    fun placeOrderByCart(addressId: Int){
        viewModelScope.launch {
            orderRepository.placeOrderByCart(addressId)
        }
    }

    fun deleteAddress(addressId: Int){
        viewModelScope.launch {
            orderRepository.deleteAddress(addressId)
        }
    }
}