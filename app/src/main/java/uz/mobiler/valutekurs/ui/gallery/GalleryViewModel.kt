package uz.mobiler.valutekurs.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.mobiler.valutekurs.models.Valute
import uz.mobiler.valutekurs.retrofit.ApiClient

class GalleryViewModel : ViewModel() {

    private var liveData=MutableLiveData<List<Valute>>()

    fun getValute():LiveData<List<Valute>> {
        ApiClient.apiService.getValute().enqueue(object : Callback<List<Valute>> {
            override fun onResponse(call: Call<List<Valute>>, response: Response<List<Valute>>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Valute>>, t: Throwable) {

            }
        })
        return liveData
    }
}