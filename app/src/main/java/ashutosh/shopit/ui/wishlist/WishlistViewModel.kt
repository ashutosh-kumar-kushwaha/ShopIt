package ashutosh.shopit.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val wishlistRepository: WishlistRepository): ViewModel() {
    val wishlistResponse get() = wishlistRepository.wishlistResponse

    fun getWishlist(){
        viewModelScope.launch {
            wishlistRepository.getWishlist()
        }
    }
}