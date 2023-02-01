package ashutosh.shopit.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.ImageItemBinding

class ImageAdapter : ListAdapter<Uri, ImageAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(uri: Uri){
            binding.imageView.setImageURI(uri)
        }
    }

    class DiffUtil: ItemCallback<Uri>(){
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("Ashu", "Checked")
    }
}