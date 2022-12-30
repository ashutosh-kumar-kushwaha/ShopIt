package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.CategoryItemBinding
import ashutosh.shopit.models.Category

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffUtil()) {
    class ViewHolder(private val binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category){
            binding.categoriesTxtVw.text = category.name
        }
    }

    class DiffUtil: ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
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