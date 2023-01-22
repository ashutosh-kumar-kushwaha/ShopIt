package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.AddressWithRadioItemBinding
import ashutosh.shopit.models.Address

class AddressOrderAdapter : ListAdapter<Address, AddressOrderAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(private val binding: AddressWithRadioItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(address: Address){
            binding.nameTxtVw.text = address.name
            val addressTxt = "${address.addressLine}, ${address.locality}, ${address.city}, ${address.state}"
            binding.addressTxtVw.text = addressTxt
        }
    }

    class DiffUtil : ItemCallback<Address>(){
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AddressWithRadioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}