package ashutosh.shopit.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.CategoryItemBinding
import ashutosh.shopit.interfaces.CategoryClickListener
import ashutosh.shopit.models.Category
import kotlin.random.Random

class CategoryAdapter(private val bgArray: List<String>, private val categoryClickListener: CategoryClickListener) : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(category: Category){
            binding.categoryBtn.text = category.categoryName
            binding.categoryBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bgArray[Random.nextInt(8)]))
        }

        override fun onClick(v: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION){
                categoryClickListener.onCategoryClick(getItem(adapterPosition).categoryId)
            }
        }
    }

    class DiffUtil: ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}