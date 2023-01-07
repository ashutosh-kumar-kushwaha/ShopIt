package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.CategoryResponse
import ashutosh.shopit.models.ProductsResponse

class HomePageRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)
    val getProductsResponseLiveData = SingleLiveEvent<NetworkResult<ProductsResponse>>()
    val getCategoriesResponseLiveData = SingleLiveEvent<NetworkResult<CategoryResponse>>()


    suspend fun getProductsByCategory(categoryId : Int){
        getProductsResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getProductsByCategory(categoryId)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        getProductsResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                    else{
                        getProductsResponseLiveData.value = NetworkResult.Error("Something went wrong!\nResponse is null")
                    }
                }
                else -> getProductsResponseLiveData.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun getCategories(){
        getCategoriesResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getCategory()
            if(response.code() == 200){
                if(response.body() != null){
                    getCategoriesResponseLiveData.value = NetworkResult.Success(response.body()!!)
                }
                else{
                    getCategoriesResponseLiveData.value = NetworkResult.Error("Something went wrong!\nResponse is null")
                }
            }
            else{
                getCategoriesResponseLiveData.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch(e: Exception){
            getCategoriesResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun getAllProducts(){
        getProductsResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getAllProducts()
            if(response.code() == 200){
                if(response.body()!=null){
                    getProductsResponseLiveData.value = NetworkResult.Success(response.body()!!)
                }
                else{
                    getProductsResponseLiveData.value = NetworkResult.Error("Something went wrong!\nResponse is null")
                }
            }
            else{
                getProductsResponseLiveData.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

}