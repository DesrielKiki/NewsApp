package desriel.kiki.newsapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {


    companion object {
        private const val BASE_URL = "https://tamasya.technice.id/api/mobile/"

        fun getApiService(): ApiService {
            //API response interceptozr
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            //client
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            //retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }

}
