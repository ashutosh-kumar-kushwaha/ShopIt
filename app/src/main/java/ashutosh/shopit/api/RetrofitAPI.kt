package ashutosh.shopit.api

import ashutosh.shopit.models.Email
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.models.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("api/auth/signupEmail")
    suspend fun signUpEmail(@Body email : Email) : Response<SignUpResponse>

    @POST("api/auth/forget")
    suspend fun forgotPassword(@Body email : Email) : Response<SignUpResponse>
 }