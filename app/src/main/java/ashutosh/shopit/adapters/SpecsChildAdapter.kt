package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.SpecsChildItemBinding
import ashutosh.shopit.models.Body
import ashutosh.shopit.models.Property

class SpecsChildAdapter(private val propertiesList : List<Body>) : RecyclerView.Adapter<SpecsChildAdapter.ViewHolder>() {

    class ViewHolder(val binding: SpecsChildItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(body: Body){
            binding.propertyNameTxtVw.text = body.head
            binding.propertyValueTxtVw.text = body.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SpecsChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(propertiesList[position])
    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

}