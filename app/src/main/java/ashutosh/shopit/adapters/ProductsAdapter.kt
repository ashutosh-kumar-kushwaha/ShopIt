package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.interfaces.ProductClickListener
import ashutosh.shopit.databinding.ItemCardBinding
import ashutosh.shopit.models.Product
import coil.load
import kotlin.math.roundToInt

class ProductsAdapter(val productClickListener: ProductClickListener) : ListAdapter<Product, ProductsAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding : ItemCardBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(product: Product){
            binding.productImgVw.load(product.imageUrls.imageUrl)
            val discountedPrice = "₹${(product.originalPrice-(product.offerPercentage/100)*product.originalPrice).roundToInt()}"
            binding.discountedPriceTxtVw.text = discountedPrice
            binding.ratingTxtVw.text = product.rating.toString()
            val soldText = "${product.noOfOrders} sold"
            binding.soldTxtVw.text = soldText
            binding.productTitleTxtVw.text = product.productName
            val originalPrice = "₹${product.originalPrice.roundToInt()}"
            binding.originalPriceTxtVw.text = originalPrice
        }

        override fun onClick(v: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION){
                productClickListener.onProductClick(getItem(adapterPosition).productId)
            }
        }
    }

    class DiffUtil: ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
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
        holder.bind(getItem(holder.adapterPosition))
    }
}