package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.AddressItemBinding
import ashutosh.shopit.interfaces.AddressClickListener
import ashutosh.shopit.models.Address

class AddressProfileAdapter(private val addressClickListener: AddressClickListener) : ListAdapter<Address, AddressProfileAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(private val binding: AddressItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(address: Address){
            binding.nameTxtVw.text = address.name
            val addressTxt = "${address.addressLine}, ${address.locality}, ${address.city}, ${address.state}"
            binding.addressTxtVw.text = addressTxt
            binding.crossBtn.setOnClickListener {
                addressClickListener.onDeleteClick(address.id)
            }
        }

        override fun onClick(v: View?){}
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
        return ViewHolder(AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}