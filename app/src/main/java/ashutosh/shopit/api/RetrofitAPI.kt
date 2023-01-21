package ashutosh.shopit.api

import ashutosh.shopit.models.*
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body

interface RetrofitAPI {

    //auth

    @Headers("isAuthorized: false")
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/signupEmail/user")
    suspend fun signUpEmail(@Body email : Email) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/forget")
    suspend fun forgotPassword(@Body email : Email) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/verifyPassOtp")
    suspend fun verifyForgotPasswordOtp(@Body verifyForgotPasswordOtpRequest: VerifyOtpRequest) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/resetpass")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/verifyotp")
    suspend fun verifySignUpOtp(@Body verifySignUpOtpRequest: VerifyOtpRequest) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/signupUser/user")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : Response<DefaultResponse>

    @Headers("isAuthorized: false")
    @POST("api/auth/signGoogle")
    suspend fun signGoogle(@Query("TokenG") token : String) : Response<LoginResponse>

    @Headers("isAuthorized: false")
    @GET("api/auth/regenerateToken")
    suspend fun regenerateAccessToken(@Query("token") refreshToken: String) : Response<LoginResponse>

    //Product

    @Headers("isAuthorized: false")
    @GET("products/getProductsByCategory/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId : Int) : Response<ProductsResponse>

    @Headers("isAuthorized: false")
    @GET("products/get")
    suspend fun getAllProducts() : Response<ProductsResponse>

    @Headers("isAuthorized: false")
    @GET("products/get/{productId}")
    suspend fun getProductDetails(@Path ("productId") productId : Int) : Response<ProductDetailsResponse>

    @Headers("isAuthorized: true")
    @GET("products/addToCart/{productId}")
    suspend fun addToCart(@Path("productId") productId: Int) : Response<DefaultResponse>

    //Category

    @Headers("isAuthorized: false")
    @GET("category/get?pageSize=5")
    suspend fun getCategory() : Response<CategoryResponse>


    //Advertisement

    @Headers("isAuthorized: false")
    @GET("sponsor/get/1")
    suspend fun getAdvertisements(@Header("Authorization") token: String) : Response<AdvertisementResponse>
}