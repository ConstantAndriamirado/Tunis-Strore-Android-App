package tunisStore.app.ui.data.remote

import retrofit2.http.GET
import tunisStore.app.ui.data.AppData

interface AppApiService {
    @GET("/api/apks")
    suspend fun getApps(): List<AppData>
}