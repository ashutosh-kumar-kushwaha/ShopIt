package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.AdvertisementResponse
import ashutosh.shopit.models.CategoryResponse
import ashutosh.shopit.models.ProductsResponse
import javax.inject.Inject

class HomePageRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val getProductsResponseLiveData = SingleLiveEvent<NetworkResult<ProductsResponse>>()
    val getCategoriesResponseLiveData = SingleLiveEvent<NetworkResult<CategoryResponse>>()
    val getAdvertisementResponseLiveData = SingleLiveEvent<NetworkResult<AdvertisementResponse>>()

    suspend fun getProductsByCategory(categoryId : Int){
        getProductsResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getProductsByCategory(categoryId)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        getProductsResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        getProductsResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                    }
                }
                else -> getProductsResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun getCategories(){
        getCategoriesResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getCategory()
            if(response.code() == 200){
                if(response.body() != null){
                    getCategoriesResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                }
                else{
                    getCategoriesResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                }
            }
            else{
                getCategoriesResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch(e: Exception){
            getCategoriesResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun getAllProducts(){
        getProductsResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getAllProducts()
            if(response.code() == 200){
                if(response.body()!=null){
                    getProductsResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                }
                else{
                    getProductsResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                }
            }
            else{
                getProductsResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun getAdvertisements(){
        getAdvertisementResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getAdvertisements()
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        getAdvertisementResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        getAdvertisementResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                    }
                }
                else -> {
                    getAdvertisementResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch(e: Exception){
            getAdvertisementResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

}