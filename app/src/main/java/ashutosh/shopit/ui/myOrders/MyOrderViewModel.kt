package ashutosh.shopit.ui.myOrders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.MyOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor(private val myOrderRepository: MyOrderRepository) : ViewModel() {
    val myOrdersResponse get() =  myOrderRepository.myOrdersResponse

    fun getAllOrders(){
        viewModelScope.launch {
            myOrderRepository.getAllOrders()
        }
    }
}