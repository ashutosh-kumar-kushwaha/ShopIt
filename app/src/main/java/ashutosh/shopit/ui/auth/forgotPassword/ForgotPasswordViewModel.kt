package ashutosh.shopit.ui.auth.forgotPassword

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.ForgotPasswordRepository

class ForgotPasswordViewModel : ViewModel() {

    private val forgotPasswordRepository = ForgotPasswordRepository()

    val forgotPasswordResponseLiveData get() = forgotPasswordRepository.forgotPasswordResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    val emailLiveData = MutableLiveData("")

    suspend fun forgotPassword(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            forgotPasswordRepository.forgotPassword(emailLiveData.value!!)
        }
        else{
            _errorMessage.value = "Enter a valid email"
        }
    }

}