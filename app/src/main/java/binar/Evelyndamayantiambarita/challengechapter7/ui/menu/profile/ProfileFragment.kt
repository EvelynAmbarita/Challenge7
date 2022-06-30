package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.profile

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.UpdateProfileRequest
import binar.Evelyndamayantiambarita.challengechapter7.databinding.FragmentProfileBinding
import binar.Evelyndamayantiambarita.challengechapter7.ui.signin.SigninActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.onViewLoaded()
        bindView()
        bindViewModel()

        return root
    }

    private fun bindView() {
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
                uri.let {
                    val type = requireActivity().contentResolver.getType(it)

                    val tempFile = File.createTempFile("temp-", null, null)
                    val inputStream = requireActivity().contentResolver.openInputStream(uri)

                    tempFile.outputStream().use { inputStream?.copyTo(it) }

                    val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                    val body = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)

                    viewModel.uploadImage(body)
                }
            }

        binding.ivAccountProfilePicture.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnAccountLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.btnAccountUpdate.setOnClickListener {
            val profile = UpdateProfileRequest(
                name = binding.etAccountNickname.text.toString(),
                job = binding.etLoginJob.text.toString()
            )
            viewModel.updateProfile(profile = profile, image = null)
            viewModel.updateDatabase(
                id = binding.tvUserId.text.toString(),
                name = binding.etAccountNickname.text.toString(),
                job = binding.etLoginJob.text.toString(),
            )
        }
    }


    private fun bindViewModel() {
        viewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowSuccess.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
        viewModel.shouldShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvUserId.text = it.id

            binding.etAccountNickname.setText(it.name)
            binding.etLoginEmail.setText(it.email)
            binding.etLoginJob.setText(it.job)

            Glide.with(requireContext())
                .load(it.image)
                .circleCrop()
                .into(binding.ivAccountProfilePicture)

        }
        viewModel.shouldShowLogin.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(requireContext(), SigninActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}