package uz.devapp.foodexpress.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.foodexpress.databinding.ActivitySiginBinding
import uz.devapp.foodexpress.models.request.LoginRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.LoginResponse
import uz.devapp.foodexpress.networking.Api
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.Constants.BASE_URL
import uz.devapp.foodexpress.utils.PrefUtils

class SiginActivity : AppCompatActivity() {
    lateinit var binding: ActivitySiginBinding
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            btnLogin.setOnClickListener {
                compositeDisposable.clear()
                compositeDisposable.add(
                    NetworkingObject.getClientInstance().login(LoginRequest(edPassword.text.toString(), edPhone.text.toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            flProgress.visibility = View.VISIBLE
                        }
                        .doOnComplete {
                            flProgress.visibility = View.GONE
                        }
                        .subscribeWith(object : DisposableObserver<BaseResponse<LoginResponse?>>() {
                            override fun onNext(t: BaseResponse<LoginResponse?>) {
                                if (t.success) {
                                    PrefUtils.setToken(t.data!!.token)
                                    startActivity(
                                        Intent(
                                            this@SiginActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            }

                            override fun onError(e: Throwable) {
                                Toast.makeText(
                                    this@SiginActivity,
                                    e.localizedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onComplete() {

                            }
                        })
                )
            }

            tvRegistration.setOnClickListener {
                startActivity(Intent(this@SiginActivity, RegistrationActivity::class.java))
                finish()
            }
        }
    }
}