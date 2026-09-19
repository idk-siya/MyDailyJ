package com.example.poefn.ui.login

import com.example.poefn.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // TODO: point this at your real MyDailyJ backend.
    // 10.0.2.2 is how the Android emulator reaches "localhost" on the host machine.
    private const val BASE_URL = "http://10.0.2.2:5000/api/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
