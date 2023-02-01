package ashutosh.shopit.ui.review

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Network
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.URIPathHelper
import ashutosh.shopit.adapters.ImageAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentReviewBinding
import ashutosh.shopit.models.AddReviewRequest
import coil.load
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private var _binding : FragmentReviewBinding? = null
    private val binding: FragmentReviewBinding get() = _binding!!
    private val imageAdapter = ImageAdapter()
    private val imageList = mutableListOf<Uri>()
    private lateinit var startForResult : ActivityResultLauncher<Intent>

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
            startForResult = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val imageUri = result.data?.data
                    imageList.add(imageUri!!)
                    imageAdapter.submitList(imageList + listOf())
                }
            }
            startForResult.launch(intent)
//            startActivityForResult(intent, 200)
        }

        binding.submitBtn.setOnClickListener{
            upload()
        }


        binding.imagesRecyclerView.adapter = imageAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 5)

        return binding.root
    }

    private fun upload(){
        val uriPathHelper = URIPathHelper()
        val images = mutableListOf<MultipartBody.Part>()
        imageList.forEachIndexed { index, uri ->
            val path = uriPathHelper.getPath(requireContext(), uri)
            val file = File(path!!)
            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData("images", file.name, requestBody)
            images.add(imageMultipart)
        }
        val requestBody = JsonObject()
        requestBody.addProperty("rating", "5")
        requestBody.addProperty("description", "Nice Product")

        val reviewRequest = requestBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
        reviewViewModel.addReview(images, reviewRequest)
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == 200 && resultCode == RESULT_OK){
//            val imageUri = data?.data
//            imageList.add(imageUri!!)
//            imageAdapter.submitList(imageList + listOf())
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}