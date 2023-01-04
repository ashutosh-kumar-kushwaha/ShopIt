package ashutosh.shopit.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.NotificationAdapter
import ashutosh.shopit.databinding.FragmentNotificationBinding
import ashutosh.shopit.models.NotificationData

class NotificationFragment : Fragment() {

    private var _binding : FragmentNotificationBinding? = null
    private val binding : FragmentNotificationBinding get() = _binding!!
    private val adapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        binding.notificationRecyclerView.adapter = adapter
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(listOf(NotificationData(1, "Order Delivered!", "8 days ago","Your shopit order containing Apple Iphone 14 128GB (Product) RED  has been delivered.", R.drawable.iphone), NotificationData(2, "Order Delivered!", "8 days ago","Your shopit order containing Apple Iphone 14 128GB (Product) RED  has been delivered.", R.drawable.iphone), NotificationData(3, "Order Delivered!", "8 days ago","Your shopit order containing Apple Iphone 14 128GB (Product) RED  has been delivered.", R.drawable.iphone), NotificationData(4, "Order Delivered!", "8 days ago","Your shopit order containing Apple Iphone 14 128GB (Product) RED  has been delivered.", R.drawable.iphone)))

        return binding.root
    }
}