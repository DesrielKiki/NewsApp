package desriel.kiki.newsapp.networking

import desriel.kiki.newsapp.model.DetailResponse
import desriel.kiki.newsapp.model.NewsData
import desriel.kiki.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("news")
    fun getNewsData(): Call<NewsResponse>
    @GET("news/{id}")
    fun getDetailData(@Path("id") id: String?): Call<DetailResponse>
}
