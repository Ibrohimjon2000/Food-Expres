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
import uz.devapp.foodexpress.databinding.ActivityRegistrationBinding
import uz.devapp.foodexpress.models.request.RegistrationRequest
import uz.devapp.foodexpress.models.response.BaseResponse
import uz.devapp.foodexpress.models.response.RegistrationResponse
import uz.devapp.foodexpress.networking.Api
import uz.devapp.foodexpress.networking.NetworkingObject
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {


            btnRegistration.setOnClickListener {
                if (edPassword.text.toString() == edRepassword.text.toString()) {
                    compositeDisposable.clear()
                    compositeDisposable.add(
                        NetworkingObject.getClientInstance().registration(
                            RegistrationRequest(
                                edFullname.text.toString(),
                                edPhone.text.toString(),
                                edPassword.text.toString()
                            )
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe {
                                flProgress.visibility = View.VISIBLE
                            }
                            .doOnComplete {
                                flProgress.visibility = View.GONE
                            }
                            .subscribeWith(object :
                                DisposableObserver<BaseResponse<RegistrationResponse?>>() {
                                override fun onNext(t: BaseResponse<RegistrationResponse?>) {
                                    if (t.success) {
                                        PrefUtils.setToken(t.data!!.token)
                                        startActivity(
                                            Intent(
                                                this@RegistrationActivity,
                                                MainActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                }

                                override fun onError(e: Throwable) {
                                    Toast.makeText(
                                        this@RegistrationActivity,
                                        e.localizedMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onComplete() {

                                }
                            })
                    )
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
}