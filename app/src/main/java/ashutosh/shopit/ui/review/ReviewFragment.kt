package ashutosh.shopit.ui.review

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.adapters.ImageAdapter
import ashutosh.shopit.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    private var _binding : FragmentReviewBinding? = null
    private val binding: FragmentReviewBinding get() = _binding!!
    private val imageAdapter = ImageAdapter()
    private val imageList = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)

        binding.addPhotoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 200)
        }

        binding.imagesRecyclerView.adapter = imageAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 5)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 200 && resultCode == RESULT_OK){
            val imageUri = data?.data
            imageList.add(imageUri!!)
            imageAdapter.submitList(imageList + listOf())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}