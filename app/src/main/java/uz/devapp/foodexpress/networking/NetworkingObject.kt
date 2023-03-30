package uz.devapp.foodexpress.networking

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.foodexpress.MyApp
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.Constants.BASE_URL
import uz.devapp.foodexpress.utils.PrefUtils

object NetworkingObject {
    private var client: Api? = null
    fun initClient() {

        client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Token", PrefUtils.getToken())
                        .addHeader("Key", Constants.DEVELOPER_KEY)
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(
                    ChuckerInterceptor.Builder(MyApp.app)
                        .collector(ChuckerCollector(MyApp.app))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    fun getClientInstance(): Api {
        if (client == null) {
            initClient()
        }
        return client!!
    }
}