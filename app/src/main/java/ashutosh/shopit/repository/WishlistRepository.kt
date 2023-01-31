package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.ProductsResponse
import javax.inject.Inject

class WishlistRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val wishlistResponse = SingleLiveEvent<NetworkResult<ProductsResponse>>()

    suspend fun getWishlist(){
        wishlistResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getWishlist()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        wishlistResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        wishlistResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    wishlistResponse.value = NetworkResult.Error(401, "Session expired")
                }
                else -> {
                    wishlistResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            wishlistResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}