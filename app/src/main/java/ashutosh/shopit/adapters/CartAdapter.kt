package ashutosh.shopit.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.CartItemBinding
import ashutosh.shopit.interfaces.ChangeProductQuantity
import ashutosh.shopit.models.CartContent
import ashutosh.shopit.models.CartItem
import coil.load
import kotlin.math.roundToInt

class CartAdapter(val changeProductQuantity: ChangeProductQuantity): ListAdapter<CartContent, CartAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cartContent: CartContent){
            binding.productImgVw.load(cartContent.product.imageUrls.imageUrl)
            binding.productTitleTxtVw.text = cartContent.product.productName
            if(cartContent.available){
                binding.stockTxtVw.text = "In Stock"
            }
            else{
                binding.stockTxtVw.text = "Out of stock"
            }
            binding.quantityTxtVw.text = cartContent.noOfProducts.toString()
            val discountPrice = "₹${price((cartContent.product.originalPrice-(cartContent.product.offerPercentage/100)*cartContent.product.originalPrice).roundToInt())}"
            binding.priceTxtVw.text = discountPrice
            binding.plusBtn.setOnClickListener {
                changeProductQuantity.increaseProductQuantity(cartContent.product.productId)
            }
            binding.minusBtn.setOnClickListener {
                changeProductQuantity.decreaseProductQuantity(cartContent.product.productId)
            }
        }

        private fun price(p : Int): String{
            val str = StringBuilder(p.toString().reversed())
            var count = 0
            var i = 1
            while(i < str.length){
                if(count == 2){
                    str.insert(i, ',')
                    i++
                    count = 0
                }
                i++
                count++
            }
            return str.reverse().toString()
        }

    }

    class DiffUtil: ItemCallback<CartContent>(){
        override fun areItemsTheSame(oldItem: CartContent, newItem: CartContent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartContent, newItem: CartContent): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}