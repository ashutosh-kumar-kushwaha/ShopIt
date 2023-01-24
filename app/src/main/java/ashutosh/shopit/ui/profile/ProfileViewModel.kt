package ashutosh.shopit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    val profileResponse get() = profileRepository.profileResponse

    fun getProfile(){
        viewModelScope.launch {
            profileRepository.getProfile()
        }
    }
}