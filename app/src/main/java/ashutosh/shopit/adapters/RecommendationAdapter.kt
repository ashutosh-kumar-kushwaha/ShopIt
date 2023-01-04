package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.LayoutRecommendationBinding
import ashutosh.shopit.models.Recommendation

class RecommendationAdapter : ListAdapter<Recommendation, RecommendationAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(private val binding : LayoutRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: Recommendation){
            binding.productImgVw.setImageResource(recommendation.image)
            binding.productTitleTxtVw.text = recommendation.name
        }
    }

    class DiffUtil : ItemCallback<Recommendation>(){
        override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}