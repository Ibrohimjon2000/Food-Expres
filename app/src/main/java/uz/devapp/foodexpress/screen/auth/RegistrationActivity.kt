package uz.devapp.foodexpress.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.foodexpress.adapters.SlideAdapter
import uz.devapp.foodexpress.databinding.ActivityRegistrationBinding
import uz.devapp.foodexpress.models.request.RegistrationRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.networking.Api
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.screen.main.main.MainViewModel
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel =
                ViewModelProvider(this@RegistrationActivity)[RegistrationViewModel::class.java]

            viewModel.errorLiveData.observe(this@RegistrationActivity) {
                Toast.makeText(this@RegistrationActivity, it, Toast.LENGTH_SHORT).show()
                binding.flProgress.visibility = View.GONE
            }

            viewModel.registrationLiveData.observe(this@RegistrationActivity) {
                PrefUtils.setToken(it!!.token)
                startActivity(
                    Intent(
                        this@RegistrationActivity,
                        MainActivity::class.java
                    )
                )
                finish()
            }

            btnRegistration.setOnClickListener {
                if (edPassword.text.toString() == edRepassword.text.toString()) {
                    loadData()
                } else {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Iltimos parolni tekshiring",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            tvLogin.setOnClickListener {
                startActivity(Intent(this@RegistrationActivity, SiginActivity::class.java))
                finish()
            }
        }
    }

    fun loadData() {
        binding.flProgress.visibility = View.VISIBLE
        viewModel.getRegistration(
            RegistrationRequest(
                binding.edFullname.text.toString(),
                binding.edPhone.text.toString(),
                binding.edPassword.text.toString()
            )
        )
    }
}