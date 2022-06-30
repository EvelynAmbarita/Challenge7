package binar.Evelyndamayantiambarita.challengechapter7.ui.signin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import binar.Evelyndamayantiambarita.challengechapter7.databinding.ActivitySigninBinding
import binar.Evelyndamayantiambarita.challengechapter7.ui.menu.MenuActivity
import binar.Evelyndamayantiambarita.challengechapter7.ui.signup.SignupActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val viewModel: SigninViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.onViewLoaded()

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.etSigninEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        binding.etSigninPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        binding.btnSigninSignin.setOnClickListener {
            viewModel.onClickSignIn()
        }

        binding.tvSigninRegister.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldOpenMenuPage.observe(this) {
            val intent = Intent(this, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Sign in...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }
    }

}