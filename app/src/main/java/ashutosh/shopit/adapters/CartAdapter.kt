package ashutosh.shopit.adapters

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
            binding.priceTxtVw.text = cartContent.product.originalPrice.toString()
            binding.plusBtn.setOnClickListener {
                changeProductQuantity.increaseProductQuantity(cartContent.product.productId)
            }
            binding.minusBtn.setOnClickListener {
                decreaseQuantity()
            }
        }

        private fun decreaseQuantity(){
            var quantity = Integer.parseInt(binding.quantityTxtVw.text.toString())
            if(quantity > 0){
                quantity--
                binding.quantityTxtVw.text = quantity.toString()
            }
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