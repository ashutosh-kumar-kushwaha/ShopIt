package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.R
import ashutosh.shopit.databinding.ReviewItemBinding
import ashutosh.shopit.models.Review
import coil.load

class ReviewsAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review){
            binding.nameTxtVw.text = review.name
            binding.messageTxtVw.text = review.message
            for(image in review.images){
                val imageView = ImageView(binding.root.context)
                imageView.load(image.imageUrl)
                val lp = LinearLayout.LayoutParams(binding.root.resources.getDimensionPixelSize(R.dimen.dp_35), binding.root.resources.getDimensionPixelSize(R.dimen.dp_35))
                lp.bottomMargin = binding.root.resources.getDimensionPixelSize(R.dimen.dp_11)
                lp.marginEnd = binding.root.resources.getDimensionPixelSize(R.dimen.dp_11)
                imageView.layoutParams = lp
                binding.imageLayout.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}