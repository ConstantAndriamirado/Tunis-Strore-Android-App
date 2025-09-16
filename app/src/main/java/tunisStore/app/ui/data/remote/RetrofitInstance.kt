package tunisStore.app.ui.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AppApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.218:3333")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApiService::class.java)
    }
}