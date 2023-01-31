package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.LayoutOfferBinding
import ashutosh.shopit.models.Offer

class ProductOffersAdapter(private val offers: List<Offer>): RecyclerView.Adapter<ProductOffersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LayoutOfferBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer){
            binding.offerHeadTxtVw.text = offer.head
            binding.offerBodyTxtVw.text = offer.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount(): Int {
        return offers.size
    }
}