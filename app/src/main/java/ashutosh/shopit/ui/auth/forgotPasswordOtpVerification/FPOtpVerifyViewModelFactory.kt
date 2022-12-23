package ashutosh.shopit.ui.auth.forgotPasswordOtpVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FPOtpVerifyViewModelFactory(val email : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FPOtpVerifyViewModelFactory(email) as T
    }
}