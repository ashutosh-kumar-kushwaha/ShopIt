package ashutosh.shopit.repository

import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult

class ForgotPasswordOtpVerificationRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

//    private val _responseLiveData = MutableLiveData<NetworkResult<>>
}