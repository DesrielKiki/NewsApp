package desriel.kiki.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import desriel.kiki.newsapp.model.DetailResponse
import desriel.kiki.newsapp.model.NewsResponse
import desriel.kiki.newsapp.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MainViewModel : ViewModel() {

    private val _newsData = MutableLiveData<NewsResponse>()
    val newsData: LiveData<NewsResponse> get() = _newsData

    private val _detailData = MutableLiveData<DetailResponse>()
    val detailData: LiveData<DetailResponse> get() = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getNewsData() {

        _isLoading.value = true
        val client = ApiConfig.getApiService()

        client.getNewsData().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("data processing error")
                }
                _isLoading.value = false
                _newsData.postValue(responseBody)
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }


    fun getDetailData(newsId: String?) {

        _isLoading.value = true
        val client = ApiConfig.getApiService()

        client.getDetailData(newsId).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("data processing error")
                }
                _isLoading.value = false
                _detailData.postValue(responseBody)
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }

    private fun onError(inputMessage: String?) {
        val message =
            if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error Occurred"
            else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may be not displayed properly").toString()
        _isError.value = true
        _isLoading.value = false
    }
}