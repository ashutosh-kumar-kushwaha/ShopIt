package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.SpecsParentItemBinding
import ashutosh.shopit.models.SpecsWithHeading

class SpecsParentAdapter(private val specsList : List<SpecsWithHeading>) : RecyclerView.Adapter<SpecsParentAdapter.ViewHolder>() {
    class ViewHolder(val binding : SpecsParentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(specsWithHeading: SpecsWithHeading){
            binding.headingTxtVw.text = specsWithHeading.heading
            binding.specsRecyclerView.adapter = SpecsChildAdapter(specsWithHeading.specs)
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