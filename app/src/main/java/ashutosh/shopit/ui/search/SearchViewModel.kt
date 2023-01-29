package ashutosh.shopit.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    val searchResponse get() = searchRepository.searchResponse

//    val keywordLiveData = MutableLiveData<String>()

    var sortBy = "productId"
    var sortDir = "dsc"

    fun search(keyword: String?){
        if(!keyword.isNullOrEmpty()){
            viewModelScope.launch {
                searchRepository.search(keyword, sortBy, sortDir)
            }
        }
    }
}