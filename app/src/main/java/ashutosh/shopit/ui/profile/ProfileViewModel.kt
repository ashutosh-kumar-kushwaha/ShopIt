package ashutosh.shopit.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.models.UpdateProfileRequest
import ashutosh.shopit.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    val profileResponse get() = profileRepository.profileResponse
    val updateProfileResponse get() = profileRepository.updateProfileResponse

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
}