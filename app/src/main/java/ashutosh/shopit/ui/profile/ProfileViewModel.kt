package ashutosh.shopit.ui.profile

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.ResetEmailRequest
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
    val resendOtpResponse get() = profileRepository.resendOtpResponse

    var originalEmail = ""

    val email = MutableLiveData("")

    val timeLiveData = MutableLiveData<String>()

    val canResend = MutableLiveData<Boolean>()

    val timer = object : CountDownTimer(60000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            val min = millisUntilFinished/60000
            val sec = (millisUntilFinished/1000)%60
            timeLiveData.value = if(sec < 10){
                "0${min}:0${sec}"
            }
            else{
                "0${min}:${sec}"
            }
        }

        override fun onFinish() {
            canResend.value = true
        }
    }

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

    fun resetEmail(resetEmailRequest: ResetEmailRequest){
        viewModelScope.launch {
            profileRepository.resetEmail(resetEmailRequest)
        }
    }

    fun updateEmail(){
        viewModelScope.launch {
            profileRepository.updateEmail(Email(email.value!!))
        }
    }

    fun resendOtp(){
        if(canResend.value == true){
            viewModelScope.launch {
                profileRepository.resendOtp(Email(email.value!!))
            }
        }
    }
}