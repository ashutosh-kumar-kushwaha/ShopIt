package ashutosh.shopit.ui.review

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Network
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.URIPathHelper
import ashutosh.shopit.adapters.ImageAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentReviewBinding
import ashutosh.shopit.models.AddReviewRequest
import coil.load
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private var _binding : FragmentReviewBinding? = null
    private val binding: FragmentReviewBinding get() = _binding!!
    private val imageAdapter = ImageAdapter()
    private val imageList = mutableListOf<Uri>()

    private val reviewViewModel by viewModels<ReviewViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        binding.viewModel = reviewViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if(arguments?.getInt("productId") != null){
            reviewViewModel.productId = arguments?.getInt("productId")!!
        }
        if(arguments?.getString("productName") != null){
            reviewViewModel.productName = arguments?.getString("productName")!!
            binding.productTitleTxtVw.text = reviewViewModel.productName
        }
        if(arguments?.getString("productImage") != null){
            reviewViewModel.productImage = arguments?.getString("productImage")!!
            binding.productImgVw.load(reviewViewModel.productImage)
        }

        binding.addPhotoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 200)
        }

        binding.imagesRecyclerView.adapter = imageAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 5)

        return binding.root
    }

    private fun upload(){
        val uriPathHelper = URIPathHelper()
        val images = mutableListOf<MultipartBody.Part>()
        imageList.forEach{
            val body = uriPathHelper.getPath(requireContext(), it)?.let { it1 ->
                val file = File(it1)
                val requestBody = RequestBody.create("images/*".toMediaTypeOrNull(), file)
                images.add(MultipartBody.Part.createFormData("images", file.name, requestBody))
            }
        }
        val addReviewRequest = AddReviewRequest("5", "Nice product")
        val review = Gson().toJson(addReviewRequest)
        val reviewRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), review)
        val reviewBody = MultipartBody.Part.createFormData("reviewDto", "reviewDto", reviewRequest)
        reviewViewModel.addReview(images, reviewBody)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewViewModel.addReviewResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
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