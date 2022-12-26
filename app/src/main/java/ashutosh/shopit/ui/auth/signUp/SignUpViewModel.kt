package ashutosh.shopit.ui.auth.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.SignUpRepository

class SignUpViewModel(val email: String, val otp: String) : ViewModel() {
    private val signUpRepository = SignUpRepository()
    var gender: String? = null
    val firstNameLiveData = MutableLiveData<String>("")
    val lastNameLiveData = MutableLiveData<String>("")
    val password1LiveData = MutableLiveData<String>("")
    val password2LiveData = MutableLiveData<String>("")

    val errorMessage = SingleLiveEvent<String>()

    val signUpResponseLiveData get() = signUpRepository.signUpResponseLiveData

    suspend fun signUp() {
        if (gender != null) {
            if (!firstNameLiveData.value.isNullOrEmpty()) {
                if (!lastNameLiveData.value.isNullOrEmpty()) {
                    if (password1LiveData.value != null && password2LiveData.value != null) {
                        if (password1LiveData.value == password2LiveData.value) {
                            if (password1LiveData.value!!.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))) {
                                signUpRepository.signUp(
                                    email,
                                    otp,
                                    firstNameLiveData.value!!,
                                    lastNameLiveData.value!!,
                                    gender!!,
                                    password1LiveData.value!!
                                )
                            } else {
                                errorMessage.value =
                                    "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces and must have at least 8 characters"
                            }
                        } else {
                            errorMessage.value = "Enter same passwords in both fields"
                            Log.d("Ashu", password1LiveData.value!!)
                            Log.d("Ashu", password2LiveData.value!!)
                        }
                    } else {
                        errorMessage.value = "Enter passwords in both fields"
                    }
                } else {
                    errorMessage.value = "Enter a last name"
                }
            } else {
                errorMessage.value = "Enter a first name"
            }
        } else {
            errorMessage.value = "Select a gender"
        }
    }
}