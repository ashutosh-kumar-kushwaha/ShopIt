package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.AddressWithRadioItemBinding
import ashutosh.shopit.interfaces.AddressClickListener
import ashutosh.shopit.models.Address

class AddressOrderAdapter(private val addressClickListener: AddressClickListener, var selectedPosition : Int = -1) : ListAdapter<Address, AddressOrderAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(private val binding: AddressWithRadioItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(address: Address){
            binding.nameTxtVw.text = address.name
            val addressTxt = "${address.addressLine}, ${address.locality}, ${address.city}, ${address.state}"
            binding.addressTxtVw.text = addressTxt
            binding.radioBtn.isChecked = adapterPosition == selectedPosition
            binding.radioBtn.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    selectedPosition = adapterPosition
                    addressClickListener.onAddressClick(getItem(adapterPosition).id)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(adapterPosition != RecyclerView.NO_POSITION){
                selectedPosition = adapterPosition
                addressClickListener.onAddressClick(getItem(position).id)
            }
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