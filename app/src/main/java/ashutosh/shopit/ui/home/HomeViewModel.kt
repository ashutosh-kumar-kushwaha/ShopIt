package ashutosh.shopit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.AdvertisementResponse
import ashutosh.shopit.models.CategoryResponse
import ashutosh.shopit.models.ProductsResponse
import ashutosh.shopit.repository.HomePageRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val homePageRepository = HomePageRepository()

    val productsLiveData : SingleLiveEvent<NetworkResult<ProductsResponse>> get() = homePageRepository.getProductsResponseLiveData

    val categoriesLiveData : SingleLiveEvent<NetworkResult<CategoryResponse>> get() = homePageRepository.getCategoriesResponseLiveData

    val offersList : SingleLiveEvent<NetworkResult<AdvertisementResponse>> get()= homePageRepository.getAdvertisementResponseLiveData


    fun getProductsByCategory(categoryId : Int){
        viewModelScope.launch {
            homePageRepository.getProductsByCategory(categoryId)
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            homePageRepository.getCategories()
        }
    }

    fun getAllProducts(){
        viewModelScope.launch {
            homePageRepository.getAllProducts()
        }
    }

    fun getAdvertisements(){
        viewModelScope.launch {
            homePageRepository.getAdvertisements()
        }
    }

}