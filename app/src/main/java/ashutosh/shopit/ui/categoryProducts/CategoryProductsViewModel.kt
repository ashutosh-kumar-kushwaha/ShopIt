package ashutosh.shopit.ui.categoryProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.ProductsResponse
import ashutosh.shopit.repository.CategoryProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(private val categoryProductsRepository: CategoryProductsRepository): ViewModel() {

    val productsLiveData : SingleLiveEvent<NetworkResult<ProductsResponse>> get() = categoryProductsRepository.getProductsResponseLiveData

    var categoryId = -1
    var categoryName = ""

    fun getProductsByCategory(){
        viewModelScope.launch {
            categoryProductsRepository.getProductsByCategory(categoryId)
        }
    }

}