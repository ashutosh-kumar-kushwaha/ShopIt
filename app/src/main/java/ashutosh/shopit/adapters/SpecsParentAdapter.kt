package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.SpecsParentItemBinding
import ashutosh.shopit.models.Specification
import ashutosh.shopit.models.SpecsWithHeading

class SpecsParentAdapter(private val specsList : List<Specification>) : RecyclerView.Adapter<SpecsParentAdapter.ViewHolder>() {
    class ViewHolder(val binding : SpecsParentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(specsWithHeading: Specification){
            binding.headingTxtVw.text = specsWithHeading.head
            binding.specsRecyclerView.adapter = SpecsChildAdapter(specsWithHeading.body)
            binding.specsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SpecsParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(specsList[position])
    }

    override fun getItemCount(): Int {
        return specsList.size
    }
}