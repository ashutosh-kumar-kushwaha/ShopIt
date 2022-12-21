package ashutosh.shopit.api

import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.models.LoginUnsuccessfulResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>
 }