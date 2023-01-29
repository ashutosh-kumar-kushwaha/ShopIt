package ashutosh.shopit.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.models.UpdateOrderRequest
import ashutosh.shopit.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val paymentRepository: PaymentRepository) : ViewModel() {
    val updateOrderResponse get() = paymentRepository.updateOrderResponse

    fun updateOrder(updateOrderRequest: UpdateOrderRequest){
        viewModelScope.launch {
            paymentRepository.updateOrder(updateOrderRequest)
        }
    }

    fun updateDirectOrder(productId: Int, quantity: Int, updateOrderRequest: UpdateOrderRequest){
        viewModelScope.launch {
            paymentRepository.updateDirectOrder(productId, quantity, updateOrderRequest)
        }
    }
}