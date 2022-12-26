package ashutosh.shopit.ui.auth.getStarted

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.GetStartedRepository

class GetStartedViewModel : ViewModel() {

    private val getStartedRepository = GetStartedRepository()

    val signUpEmailResponseLiveData get() = getStartedRepository.signUpEmailResponseLiveData

    val loginResponseLiveData get() = getStartedRepository.loginResponseLiveData

    val errorMessage = SingleLiveEvent<String>()

    val emailLiveData = MutableLiveData("")

    suspend fun signUpEmail(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            getStartedRepository.signUpEmail(emailLiveData.value!!)
        }
        else{
            errorMessage.value = "Invalid email"
        }
    }

    suspend fun googleSignIn(token : String){
        getStartedRepository.signGoogle(token)
    }

}