package binar.Evelyndamayantiambarita.challengechapter7.ui.signup

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import binar.Evelyndamayantiambarita.challengechapter7.databinding.ActivitySignupBinding
import binar.Evelyndamayantiambarita.challengechapter7.ui.menu.MenuActivity
import binar.Evelyndamayantiambarita.challengechapter7.ui.signin.SigninActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()
        bindViewModel()

    }

    private fun bindView() {
        binding.etSignupNickname.doAfterTextChanged {
            viewModel.onChangeNickname(it.toString())
        }
        binding.etSignupJob.doAfterTextChanged {
            viewModel.onChangeJob(it.toString())
        }
        binding.etSignupEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etSignupPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.etSignupRetypePassword.doAfterTextChanged {
            viewModel.onChangeRetypePassword(it.toString())
        }
        binding.btnSignupRegister.setOnClickListener {
            viewModel.onValidate()
        }
        binding.tvSignupSignin.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }

        viewModel.shouldOpenUpdateProfile.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}