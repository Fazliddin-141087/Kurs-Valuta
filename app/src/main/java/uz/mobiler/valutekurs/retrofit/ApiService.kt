package uz.mobiler.valutekurs.retrofit

import retrofit2.Call
import retrofit2.http.GET
import uz.mobiler.valutekurs.models.Valute

interface ApiService {
    @GET(".")
    fun getValute(): Call<List<Valute>>

}