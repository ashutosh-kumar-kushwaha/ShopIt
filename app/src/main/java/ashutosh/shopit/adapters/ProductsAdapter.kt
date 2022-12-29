package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.ItemCardBinding
import ashutosh.shopit.models.Product

class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding : ItemCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.productImgVw.setImageResource(product.image)
            binding.discountedPriceTxtVw.text = product.discountedPrice
            binding.ratingTxtVw.text = product.rating
            binding.soldTxtVw.text = product.sold.toString()
            binding.productTitleTxtVw.text = product.name
            binding.originalPriceTxtVw.text = product.originalPrice
        }
    }

    class DiffUtil: ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}