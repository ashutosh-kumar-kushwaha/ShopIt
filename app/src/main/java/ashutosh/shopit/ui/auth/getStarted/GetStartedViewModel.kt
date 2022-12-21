package ashutosh.shopit.ui.auth.getStarted

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.GetStartedRepository

class GetStartedViewModel : ViewModel() {

    private val getStartedRepository = GetStartedRepository()

    val signUpEmailResponseLiveData get() = getStartedRepository.signUpEmailResponseLiveData
    val errorMessage get() = getStartedRepository.errorMessage

    val emailLiveData = MutableLiveData("")

    suspend fun signUpEmail(){
        if(emailLiveData.value!=null){
            getStartedRepository.signUpEmail(emailLiveData.value!!)
        }
    }

}