package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult

class HomePageRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)
    val getProductsByCategoryResponseLiveData = SingleLiveEvent<NetworkResult<CategoryResponse>>()

    suspend fun getProductsByCategory(categoryId : Int){
        getProductsByCategoryResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getProductsByCategory(categoryId)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        getProductsByCategoryResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                    else{
                        getProductsByCategoryResponseLiveData.value = NetworkResult.Error("Something went wrong!\nResponse is null")
                    }
                }
                else -> getProductsByCategoryResponseLiveData.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsByCategoryResponseLiveData.value = NetworkResult.Error(e.message)
        }

    }

}