package ashutosh.shopit.ui.auth.forgotPassword

import android.os.CountDownTimer
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.ForgotPasswordRepository

class ForgotPasswordViewModel : ViewModel() {

    private val forgotPasswordRepository = ForgotPasswordRepository()

    val forgotPasswordResponseLiveData get() = forgotPasswordRepository.forgotPasswordResponseLiveData

    val errorMessage = SingleLiveEvent<String>()

    val emailLiveData = MutableLiveData("")

    suspend fun forgotPassword(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            forgotPasswordRepository.forgotPassword(emailLiveData.value!!)
        }
        else{
            errorMessage.value = "Enter a valid email"
        }
    }

}