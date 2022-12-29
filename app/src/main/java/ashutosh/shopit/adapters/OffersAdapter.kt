package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.OfferItemBinding

class OffersAdapter(private val offersList : List<Int>) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding : OfferItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(offerImg : Int){
            binding.offerImgVw.setImageResource(offerImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(offersList[position])
    }

    override fun getItemCount(): Int {
        return offersList.size
    }
}