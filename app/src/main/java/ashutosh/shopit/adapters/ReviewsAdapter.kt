package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.R
import ashutosh.shopit.databinding.ReviewItemBinding
import ashutosh.shopit.models.Review
import coil.load

class ReviewsAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review){
            val name = "${review.user.firstname} ${review.user.lastname}"
            binding.nameTxtVw.text = name
            binding.messageTxtVw.text = review.description
            binding.imageRecyclerView.layoutManager = GridLayoutManager(binding.root.context, 6, GridLayoutManager.VERTICAL, false)
            binding.imageRecyclerView.adapter = ReviewImageAdapter(review.images)
            binding.ratingBar.rating = review.rating.toFloat()
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