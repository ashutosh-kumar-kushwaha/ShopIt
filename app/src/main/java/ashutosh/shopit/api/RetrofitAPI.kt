package ashutosh.shopit.api

import ashutosh.shopit.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    @GET("products/getProductsByCategory/{categoryId}?pageNumber=0&pageSize=100")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId : Int, @Query("sortBy") sortBy: String = "productId", @Query("sortDir") sortDir: String = "dsc") : Response<ProductsResponse>

    @Headers("isAuthorized: false")
    @GET("products/get?pageNumber=0&pageSize=100")
    suspend fun getAllProducts() : Response<ProductsResponse>

    @Headers("isAuthorized: false")
    @GET("products/get/{productId}")
    suspend fun getProductDetails(@Path ("productId") productId : Int) : Response<ProductDetailsResponse>

    @Headers("isAuthorized: true")
    @POST("products/addToCart/{productId}/quantity/{quantity}")
    suspend fun addToCart(@Path("productId") productId: Int, @Path("quantity") quantity: Int) : Response<DefaultResponse>

    @Headers("isAuthorized: true")
    @GET("products/getReview/{productId}")
    suspend fun getReviews(@Path("productId") productId: Int): Response<ReviewResponse>

    @Headers("isAuthorized: true")
    @PATCH("products/wishlist/add/{productId}")
    suspend fun addToWishlist(@Path("productId") productId: Int): Response<DefaultResponse>

    @Multipart
    @Headers("isAuthorized: true")
    @POST("products/review/add/{productId}")
    suspend fun addReview(@Path("productId") productId: Int, @Part images: List<MultipartBody.Part>, @Part("rating") rating: RequestBody, @Part("description") description: RequestBody) : Response<AddReviewResponse>


    @Headers("isAuthorized: true")
    @GET("products/getFAQs/{productId}?pageNumber=0&pageSize=100")
    suspend fun getQuestionsAnswers(@Path("productId") productId: Int) : Response<QuestionAnswerResponse>

    //Category

    @Headers("isAuthorized: false")
    @GET("category/get?pageSize=100")
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

    @Headers("isAuthorized: true")
    @POST("api/profile/addAddress")
    suspend fun addAddress(@Body addAddressRequest: AddAddressRequest): Response<List<Address>>

    @Headers("isAuthorized: true")
    @DELETE("api/profile/removeAddress/{addressId}")
    suspend fun deleteAddress(@Path ("addressId") addressId: Int) : Response<DefaultResponse>

    //Payment

    @Headers("isAuthorized: true")
    @POST("payment/cart/createOrder/{addressId}")
    suspend fun placeOrderByCart(@Path("addressId") addressId: Int) : Response<OrderResponse>

    @Headers("isAuthorized: true")
    @POST("payment/createOrder/{productId}/quantity/{quantity}/address/{addressId}")
    suspend fun directPlaceOrder(@Path("productId") productId: Int, @Path("quantity") quantity: Int, @Path("addressId") addressId: Int) : Response<OrderResponse>

    @Headers("isAuthorized: true")
    @POST("payment/update_order")
    suspend fun updateOrder(@Body updateOrderRequest: UpdateOrderRequest) : Response<UpdateOrderResponse>

    @Headers("isAuthorized: true")
    @POST("payment/update_single_order/product/{productId}/quantity/{quantity}")
    suspend fun updateDirectOrder(@Path("productId") productId: Int, @Path("quantity") quantity: Int, @Body updateOrderRequest: UpdateOrderRequest) : Response<UpdateOrderResponse>

    //Search

    @Headers("isAuthorized: false")
    @GET("products/search/{keyword}?pageNumber=0&pageSize=100")
    suspend fun search(@Path("keyword") keyword: String, @Query("sortBy") sortBy: String, @Query("sortDir") sortDir: String): Response<ProductsResponse>

    //profile

    @Headers("isAuthorized: true")
    @GET("api/profile/getProfile")
    suspend fun getProfile() : Response<Profile>

    @Headers("isAuthorized: true")
    @PUT("api/profile/updateProfile")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest) : Response<Profile>

    @Headers("isAuthorized: true")
    @POST("api/profile/sendEmailOTP")
    suspend fun updateEmail(@Body email: Email) : Response<DefaultResponse>

    @Headers("isAuthorized: true")
    @PATCH("api/profile/resetEmailID")
    suspend fun resetEmail(@Body resetEmailRequest: ResetEmailRequest) : Response<LoginResponse>

    @Headers("isAuthorized: true")
    @GET("payment/getAllOrders?pageNumber=0&pageSize=100")
    suspend fun getAllOrders() : Response<MyOrderResponse>

    @Headers("isAuthorized: true")
    @GET("products/wishlist/get")
    suspend fun getWishlist() : Response<ProductsResponse>

    @Multipart
    @Headers("isAuthorized: true")
    @PATCH("api/profile/updateProfilePhoto")
    suspend fun changeProfilePic(@Part image: MultipartBody.Part) : Response<ChangeProfilePicResponse>

}