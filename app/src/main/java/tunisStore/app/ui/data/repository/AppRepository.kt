package tunisStore.app.ui.data.repository

import tunisStore.app.ui.data.AppData
import tunisStore.app.ui.data.remote.RetrofitInstance

class AppRepository {
    suspend fun getApps(): List<AppData> {
        return RetrofitInstance.api.getApps()
    }
}