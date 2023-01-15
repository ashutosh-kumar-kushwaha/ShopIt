package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.DescriptionItemBinding
import ashutosh.shopit.models.Description

class DescriptionAdapter(val descriptionList : List<Description>) : RecyclerView.Adapter<DescriptionAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: DescriptionItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(description: Description){
            binding.headingTxtVw.text = description.head
            binding.contentTxtVw.text = description.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DescriptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(descriptionList[position])
    }

    override fun getItemCount(): Int {
        return descriptionList.size
    }
}