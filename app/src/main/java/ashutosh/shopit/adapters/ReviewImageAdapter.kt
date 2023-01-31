package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.ReviewImageBinding
import ashutosh.shopit.models.Image
import coil.load

class ReviewImageAdapter(private val images: List<Image>) : RecyclerView.Adapter<ReviewImageAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ReviewImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image){
            binding.imageView.load(image.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReviewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}