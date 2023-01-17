package ashutosh.shopit.ui.auth.resetPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.ResetPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private var resetPasswordRepository : ResetPasswordRepository) : ViewModel() {

    var email = ""
    var otp = ""

    val resetPasswordResponseLiveData get() = resetPasswordRepository.resetPasswordResponseLiveData

    val errorMessage = SingleLiveEvent<String>()

    val password1LiveData = MutableLiveData("")
    val password2LiveData = MutableLiveData("")

    suspend fun resetPassword(){
        if(password1LiveData.value != null && password2LiveData.value != null){
            if(password1LiveData.value == password2LiveData.value){
                if(password1LiveData.value!!.length < 8){
                    if(password1LiveData.value!!.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))){
                        resetPasswordRepository.resetPassword(email, otp, password1LiveData.value!!)
                    }
                    else{
                        errorMessage.value = "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces"
                    }
                }
                else{
                    errorMessage.value = "Password must contain at least 8 characters"
                }
            }
            else{
                errorMessage.value = "Enter same passwords in both fields"
            }
        }
        else{
            errorMessage.value = "Enter passwords in both fields"
        }
    }

}