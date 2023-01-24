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
    @POST("products/addToCart/{productId}")
    suspend fun addToCart(@Path("productId") productId: Int) : Response<DefaultResponse>

    //Category

    @Headers("isAuthorized: false")
    @GET("category/get?pageSize=5")
    suspend fun getCategory() : Response<CategoryResponse>


    //Advertisement

    @Headers("isAuthorized: false")
    @GET("sponsor/get/1")
    suspend fun getAdvertisements() : Response<AdvertisementResponse>

    //Cart

    @Headers("isAuthorized: true")
    @GET("products/cart/get?pageNumber=0&pageSize=100&sortBy=Id&sortDir=asc")
    suspend fun getCartProducts() : Response<CartProductsResponse>

    @Headers("isAuthorized: true")
    @PUT("products/cart/increase/{productId}")
    suspend fun increaseProductQuantity(@Path("productId") productId: Int) : Response<DefaultResponse>

    @Headers("isAuthorized: true")
    @PUT("products/cart/decrease/{productId}")
    suspend fun decreaseProductQuantity(@Path("productId") productId: Int) : Response<DefaultResponse>

    // Address

    @Headers("isAuthorized: true")
    @GET("api/profile/address/get")
    suspend fun getAddresses() : Response<List<Address>>

    //Payment

    @Headers("isAuthorized: true")
    @POST("payment/cart/createOrder/{addressId}")
    suspend fun placeOrderByCart(@Path("addressId") addressId: Int) : Response<OrderResponse>

    @Headers("isAuthorized: true")
    @POST("payment/update_order")
    suspend fun updateOrder(@Body updateOrderRequest: UpdateOrderRequest) : Response<UpdateOrderResponse>

    //Search

    @Headers("isAuthorized: false")
    @GET("products/search/{keyword}")
    suspend fun search(@Path("keyword") keyword: String, @Query("sortBy") sortBy: String, @Query("sortDir") sortDir: String): Response<ProductsResponse>

    //profile

    @Headers("isAuthorized: true")
    @GET("api/profile/getProfile")
    suspend fun getProfile() : Response<Profile>

    @Headers("isAuthorized: true")
    @PUT("api/profile/updateProfile")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest) : Response<Profile>

    @Headers("isAuthorized: true")
    @POST("api/profile/addAddress")
    suspend fun addAddress(@Body addAddressRequest: AddAddressRequest): Response<List<Address>>

}