package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.SpecsChildItemBinding
import ashutosh.shopit.models.Property

class SpecsChildAdapter(private val propertiesList : List<Property>) : RecyclerView.Adapter<SpecsChildAdapter.ViewHolder>() {

    class ViewHolder(val binding: SpecsChildItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(property: Property){
            binding.propertyNameTxtVw.text = property.propertyName
            binding.propertyValueTxtVw.text = property.propertyValue
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