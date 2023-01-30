package ashutosh.shopit.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.OrderItemBinding
import ashutosh.shopit.models.MyOrderContent

class MyOrderAdapter : ListAdapter<MyOrderContent, MyOrderAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(private val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(order: MyOrderContent){
            binding.orderIdTxtVw.text = order.orderId
            val p = "₹${price(order.amount)}"
            binding.amountTxtVw.text = p
            binding.dateTxtVw.text = order.createdAt
            binding.statusTxtVw.text = order.status
        }
    }

    private fun price(p : Int): String{
        val str = StringBuilder(p.toString().reversed())
        var count = 0
        var i = 1
        while(i < str.length){
            if(count == 2){
                str.insert(i, ',')
                i++
                count = 0
            }
            i++
            count++
        }
        return str.reverse().toString()
    }

    class DiffUtil : ItemCallback<MyOrderContent>(){
        override fun areItemsTheSame(oldItem: MyOrderContent, newItem: MyOrderContent): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: MyOrderContent, newItem: MyOrderContent): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}