package ashutosh.shopit.ui.directOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.DirectOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectOrderViewModel @Inject constructor(private val directOrderRepository: DirectOrderRepository): ViewModel() {

    val addressResponse get() = directOrderRepository.addressResponse
    val placeOrderResponse get() = directOrderRepository.placeOrderResponse
    val deleteAddressResponse get() = directOrderRepository.deleteAddressResponse

    var productId = -1
    var quantity = -1

    fun getAddresses() {
        viewModelScope.launch {
            directOrderRepository.getAddresses()
        }
    }

    fun directPlaceOrder(addressId: Int){
        viewModelScope.launch {
            directOrderRepository.directPlaceOrder(productId, quantity, addressId)
        }
    }

    fun deleteAddress(addressId: Int){
        viewModelScope.launch {
            directOrderRepository.deleteAddress(addressId)
        }
    }
}