package ashutosh.shopit.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.UpdateProfileRequest
import ashutosh.shopit.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    val addressResponse get() = profileRepository.addressResponse
    val profileResponse get() = profileRepository.profileResponse
    val updateProfileResponse get() = profileRepository.updateProfileResponse
    val updateEmailResponse get() = profileRepository.updateEmailResponse
    val resetEmailResponse get() = profileRepository.resetEmailResponse

    var originalEmail = ""

    val email = MutableLiveData("")

    fun getProfile(){
        viewModelScope.launch {
            profileRepository.getProfile()
        }
    }

    fun updateProfile(updateProfileRequest: UpdateProfileRequest){
        viewModelScope.launch {
            profileRepository.updateProfile(updateProfileRequest)
        }
    }

    fun getAddresses(){
        viewModelScope.launch {
            profileRepository.getAddresses()
        }
    }

    fun submitEmailForUpdate(email: Email){

    }

    fun updateEmail(){
        viewModelScope.launch {
            profileRepository.updateEmail(Email(email.value!!))
        }
    }
}