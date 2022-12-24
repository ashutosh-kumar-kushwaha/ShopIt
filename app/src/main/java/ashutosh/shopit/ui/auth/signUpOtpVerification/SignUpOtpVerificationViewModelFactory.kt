package ashutosh.shopit.ui.auth.signUpOtpVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignUpOtpVerificationViewModelFactory(val email: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpOtpVerificationViewModel(email) as T
    }
}