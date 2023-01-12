package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.ProductImageItemBinding
import ashutosh.shopit.models.Image
import coil.load

class ProductImageAdapter(private val images: List<Image>) : RecyclerView.Adapter<ProductImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ProductImageItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Image){
            binding.productImgVw.load(image.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

}