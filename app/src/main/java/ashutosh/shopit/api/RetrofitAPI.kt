package ashutosh.shopit.api

import ashutosh.shopit.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    //auth
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("api/auth/signupEmail/user")
    suspend fun signUpEmail(@Body email : Email) : Response<DefaultResponse>

    @POST("api/auth/forget")
    suspend fun forgotPassword(@Body email : Email) : Response<DefaultResponse>

    @POST("api/auth/verifyPassOtp")
    suspend fun verifyForgotPasswordOtp(@Body verifyForgotPasswordOtpRequest: VerifyOtpRequest) : Response<DefaultResponse>

    @POST("api/auth/resetpass")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest) : Response<DefaultResponse>

    @POST("api/auth/verifyotp")
    suspend fun verifySignUpOtp(@Body verifySignUpOtpRequest: VerifyOtpRequest) : Response<DefaultResponse>

    @POST("api/auth/signupUser/user")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : Response<DefaultResponse>

    @POST("api/auth/signGoogle")
    suspend fun signGoogle(@Query("TokenG") token : String) : Response<LoginResponse>

    //Product

    @GET("products/getProductsByCategory/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId : Int) : Response<ProductsResponse>

    @GET("products/get")
    suspend fun getAllProducts() : Response<ProductsResponse>

    //Category

    @GET("category/get?pageSize=5")
    suspend fun getCategory() : Response<CategoryResponse>
}