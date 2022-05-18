package ikhwan.binar.miniprojectproductflavor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ikhwan.binar.chapterenam.latihanmvvm.network.ApiClient
import ikhwan.binar.latihanlistcountryretrofit.model.GetCountryResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainCountryViewModel : ViewModel() {

    val liveDataCountry = MutableLiveData<ArrayList<GetCountryResponseItem>?>()

    fun getCountry(){
        ApiClient.instance.getCountry()
            .enqueue(object : Callback<ArrayList<GetCountryResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<GetCountryResponseItem>>,
                    response: Response<ArrayList<GetCountryResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataCountry.postValue(response.body()!!)
                    }else{
                        liveDataCountry.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<GetCountryResponseItem>>,
                    t: Throwable
                ) {
                    liveDataCountry.postValue(null)
                }

            })
    }

}