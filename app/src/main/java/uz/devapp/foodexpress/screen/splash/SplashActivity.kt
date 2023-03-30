package uz.devapp.foodexpress.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.orhanobut.hawk.Hawk
import uz.devapp.foodexpress.databinding.ActivitySplashBinding
import uz.devapp.foodexpress.screen.auth.SiginActivity
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Handler().postDelayed({
                if (PrefUtils.getToken().isEmpty()){
                    startActivity(Intent(this@SplashActivity, SiginActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }, 3000)
        }
    }
}