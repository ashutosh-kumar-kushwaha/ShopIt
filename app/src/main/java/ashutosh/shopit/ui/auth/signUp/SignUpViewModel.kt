package ashutosh.shopit.ui.auth.signUp

import androidx.lifecycle.ViewModel

class SignUpViewModel(val email : String, val otp : String) : ViewModel() {
    var gender : String? = null

}