package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.NotificationItemBinding
import ashutosh.shopit.models.NotificationData

class NotificationAdapter : ListAdapter<NotificationData, NotificationAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding : NotificationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(notificationData: NotificationData){
            binding.productImgVw.setImageResource(notificationData.image)
            binding.timeTxtVw.text = notificationData.time
            binding.messageTxtVw.text = notificationData.message
            binding.titleTxtVw.text = notificationData.title
        }
    }

    class DiffUtil : ItemCallback<NotificationData>(){
        override fun areItemsTheSame(
            oldItem: NotificationData,
            newItem: NotificationData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationData,
            newItem: NotificationData
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}