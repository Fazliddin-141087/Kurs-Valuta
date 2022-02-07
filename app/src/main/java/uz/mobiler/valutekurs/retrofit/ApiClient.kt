package uz.mobiler.valutekurs.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL="https://nbu.uz/uz/exchange-rates/json/"
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    var apiService= getRetrofit().create(ApiService::class.java)
}