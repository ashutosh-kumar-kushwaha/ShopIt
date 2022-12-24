package ashutosh.shopit.ui.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.SignUpRepository

class SignUpViewModel(val email : String, val otp : String) : ViewModel() {
    private val signUpRepository = SignUpRepository()
    var gender : String? = null
    val firstNameLiveData = MutableLiveData("")
    val lastNameLiveData = MutableLiveData("")
    val password1LiveData = MutableLiveData("")
    val password2LiveData = MutableLiveData("")

    private val _errorMessage = MutableLiveData("")
    val errorMessage : LiveData<String> get() = _errorMessage

    val signUpResponseLiveData get() = signUpRepository.signUpResponseLiveData

    suspend fun signUp(){
        signUpRepository.signUp(email, otp, firstNameLiveData.value!!, lastNameLiveData.value!!, gender!!, password1LiveData.value!!)
    }
}