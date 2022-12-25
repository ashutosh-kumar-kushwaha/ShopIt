package ashutosh.shopit.ui.auth.getStarted

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.GetStartedRepository

class GetStartedViewModel : ViewModel() {

    private val getStartedRepository = GetStartedRepository()

    val signUpEmailResponseLiveData get() = getStartedRepository.signUpEmailResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get()= _errorMessage

    val emailLiveData = MutableLiveData("")

    suspend fun signUpEmail(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            getStartedRepository.signUpEmail(emailLiveData.value!!)
        }
        else{
            _errorMessage.value = "Invalid email"
        }
    }

}