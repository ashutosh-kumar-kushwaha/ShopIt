package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.ProductDetailsResponse
import ashutosh.shopit.models.QuestionAnswerResponse
import ashutosh.shopit.models.ReviewResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val productDetailsResponse = SingleLiveEvent<NetworkResult<ProductDetailsResponse>>()
    val addToCartResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()
    val reviewResponse = SingleLiveEvent<NetworkResult<ReviewResponse>>()
    val addToWishlistResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()
    val questionAnswerResponse = SingleLiveEvent<NetworkResult<QuestionAnswerResponse>>()

    suspend fun getProductDetails(productId : Int){
        productDetailsResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getProductDetails(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null){
                        productDetailsResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        productDetailsResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null}")
                    }
                }
                else -> {
                    productDetailsResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            productDetailsResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun addToCart(productId: Int, quantity: Int){
        addToCartResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.addToCart(productId, quantity)
            when(response.code()){
                200 -> {
                    if (response.body() != null) {
                        addToCartResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        addToCartResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null}")
                    }
                }
                401 -> {
                    addToCartResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                409 -> {
                    addToCartResponse.value = NetworkResult.Error(409, "Product already exist in the cart")
                }
                else -> {
                    addToCartResponse.value = NetworkResult.Error(response.code(),"Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addToCartResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun getReviews(productId: Int){
        reviewResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getReviews(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null) {
                        reviewResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        reviewResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null}")
                    }
                }
                401 -> {
                    reviewResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                409 -> {
                    reviewResponse.value = NetworkResult.Error(409, "Product already exist in the cart")
                }
                else -> {
                    reviewResponse.value = NetworkResult.Error(response.code(),"Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            reviewResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun addToWishlist(productId: Int){
        addToWishlistResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.addToWishlist(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null) {
                        addToWishlistResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        addToWishlistResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null}")
                    }
                }
                401 -> {
                    addToWishlistResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                409 -> {
                    addToWishlistResponse.value = NetworkResult.Error(409, "Product already exist in the cart")
                }
                else -> {
                    addToWishlistResponse.value = NetworkResult.Error(response.code(),"Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addToWishlistResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun getQuestionsAnswers(productId: Int){
        questionAnswerResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getQuestionsAnswers(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null) {
                        questionAnswerResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        questionAnswerResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null}")
                    }
                }
                401 -> {
                    questionAnswerResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                409 -> {
                    questionAnswerResponse.value = NetworkResult.Error(409, "Product already exist in the cart")
                }
                else -> {
                    questionAnswerResponse.value = NetworkResult.Error(response.code(),"Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            reviewResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

}