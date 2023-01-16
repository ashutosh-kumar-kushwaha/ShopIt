package ashutosh.shopit.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.CartItemBinding
import ashutosh.shopit.models.CartItem

class CartAdapter: ListAdapter<CartItem, CartAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: CartItem){
            binding.productImgVw.setImageResource(cartItem.productImage)
            binding.productTitleTxtVw.text = cartItem.productName
            binding.stockTxtVw.text = cartItem.stockDetails
            binding.priceTxtVw.text = cartItem.price.toString()
            binding.plusBtn.setOnClickListener {
                increaseQuantity()
            }
            binding.minusBtn.setOnClickListener {
                decreaseQuantity()
            }
        }

        private fun decreaseQuantity(){
            var quantity = Integer.parseInt(binding.quantityTxtVw.text.toString())
            if(quantity >= 0){
                quantity--
                binding.quantityTxtVw.text = quantity.toString()
            }
        }

        private fun increaseQuantity(){
            var quantity = Integer.parseInt(binding.quantityTxtVw.text.toString())
            quantity--
            binding.quantityTxtVw.text = quantity.toString()
        }
    }

    class DiffUtil: ItemCallback<CartItem>(){
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}