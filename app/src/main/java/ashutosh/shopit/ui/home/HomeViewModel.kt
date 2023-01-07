package ashutosh.shopit.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.R
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.models.Product
import ashutosh.shopit.repository.HomePageRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val homePageRepository = HomePageRepository()

    val productsList = SingleLiveEvent<List<Product>>()

    val offersList = SingleLiveEvent<List<Int>>()

    init {
        productsList.value = listOf(Product(1, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(2, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(3, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(4, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(5, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(6, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(7, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(8, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(9, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(10, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"))
        offersList.value = listOf(R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer)
    }

    fun getProductsByCategory(categoryId : Int){
        viewModelScope.launch {
            homePageRepository.getProductsByCategory(categoryId)
        }
    }

}