package ashutosh.shopit.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.models.AddAddressRequest
import ashutosh.shopit.repository.AddAddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(private val addAddressRepository: AddAddressRepository): ViewModel() {
    val addAddressResponse get() = addAddressRepository.addAddressResponse

    val name = MutableLiveData("")
    val type = MutableLiveData("")
    val mobile = MutableLiveData("")
    val pinCode = MutableLiveData("")
    val locality = MutableLiveData("")
    val addressLine = MutableLiveData("")
    val city = MutableLiveData("")
    val state = MutableLiveData("")
    val landmark = MutableLiveData("")
    val mobile2 = MutableLiveData("")

    fun addAddress(){
        viewModelScope.launch {
            addAddressRepository.addAddress(AddAddressRequest(type.value!!, name.value!!, mobile.value!!, pinCode.value!!, locality.value!!, addressLine.value!!, city.value!!, state.value!!, landmark.value!!, mobile2.value!!))
        }
    }
}