package com.example.home.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.database.AppDatabase
import com.example.core.database.UserViewModel
import com.example.home.databinding.FragmentProfileBinding
import com.example.home.shared.SignInActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private val userDao by lazy { AppDatabase.getInstance(requireContext()).userDao() }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Initialize the UserViewModel
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        // Access the user data from the UserViewModel
        val user = userViewModel.user
        if (user != null) {
            binding.name.text = "${user.firstname} ${user.lastname}"
        }




        binding.logout.setOnClickListener {
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePhoto.setOnClickListener {
            // Open image picker to choose or capture a photo
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected image URI
            val imageUri = data?.data ?: return
            val imagePath = getImagePathFromUri(imageUri)

            // Update the user profile image in the database
            userViewModel.user?.id?.let { userId ->
                GlobalScope.launch {
                    userDao.updateProfileImage(userId, imagePath)
                }
            }

            // Load the selected image into the UserImage ImageView
            Glide.with(binding.imageUser)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imageUser)
        }
    }

    private fun getImagePathFromUri(uri: Uri): String {
        // Get the absolute path of the image file from its content URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return ""
    }

    companion object {
        const val IMAGE_PICKER_REQUEST_CODE = 123
    }
}
