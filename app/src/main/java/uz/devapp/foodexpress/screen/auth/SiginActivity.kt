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
import uz.devapp.foodexpress.databinding.ActivitySiginBinding
import uz.devapp.foodexpress.models.request.LoginRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.networking.Api
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.screen.main.main.MainViewModel
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.Constants.BASE_URL
import uz.devapp.foodexpress.utils.PrefUtils

class SiginActivity : AppCompatActivity() {
    lateinit var binding: ActivitySiginBinding
    lateinit var viewModel: SiginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel = ViewModelProvider(this@SiginActivity)[SiginViewModel::class.java]

            viewModel.errorLiveData.observe(this@SiginActivity) {
                Toast.makeText(this@SiginActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@SiginActivity){
                if (it){
                    binding.flProgress.visibility = View.VISIBLE
                }else{
                    binding.flProgress.visibility = View.GONE
                }
            }

            viewModel.siginLiveData.observe(this@SiginActivity) {
                PrefUtils.setToken(it!!.token)
                startActivity(
                    Intent(
                        this@SiginActivity,
                        MainActivity::class.java
                    )
                )
                finish()
            }

            btnLogin.setOnClickListener {
                loadData()
            }

            tvRegistration.setOnClickListener {
                startActivity(Intent(this@SiginActivity, RegistrationActivity::class.java))
                finish()
            }
        }
    }

    fun loadData() {
        viewModel.getSigin(
            LoginRequest(
                binding.edPassword.text.toString(),
                binding.edPhone.text.toString()
            )
        )
    }
}