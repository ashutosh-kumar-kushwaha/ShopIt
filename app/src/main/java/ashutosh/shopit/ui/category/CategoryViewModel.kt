package ashutosh.shopit.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.CategoryResponse
import ashutosh.shopit.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel(){

    val categoriesLiveData : SingleLiveEvent<NetworkResult<CategoryResponse>> get() = categoryRepository.getCategoriesResponseLiveData

    fun getCategories(){
        viewModelScope.launch {
            categoryRepository.getCategories()
        }
    }

}