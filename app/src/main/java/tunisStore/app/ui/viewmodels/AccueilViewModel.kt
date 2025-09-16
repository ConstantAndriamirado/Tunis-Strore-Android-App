package tunisStore.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tunisStore.app.ui.data.AppSectionData
import tunisStore.app.ui.data.repository.AppRepository
import tunisStore.app.ui.data.mappers.toAppData
import tunisStore.app.ui.data.fakeAppSections


sealed class UiState {
    object Loading : UiState()
    data class Success(val sections: List<AppSectionData>) : UiState()
    data class Error(val message: String) : UiState()
}

class AccueilViewModel : ViewModel() {
    private val repository = AppRepository()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchApps()
    }

    fun fetchApps() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Success(fakeAppSections)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Impossible de charger les données.")
            }
        }
    }
    /*
    fun fetchApps() {
        viewModelScope.launch {
            try {
                val remoteApps = repository.getApps()
                val appDataList = remoteApps.map { it.toAppData() }
                val sections = listOf(
                    AppSectionData(
                        title = "Recommandé pour vous",
                        apps = appDataList.filter { it.category == "Productivité" || it.price != "Gratuits" }
                    ),
                    AppSectionData(
                        title = "Populaire",
                        apps = appDataList.filter { it.rating >= 4.5 }
                    ),
                    AppSectionData(
                        title = "Gratuits",
                        apps = appDataList.filter { it.price == "Gratuits" }
                    )
                ).filter { it.apps.isNotEmpty() }
                _uiState.value = UiState.Success(sections)
            } catch (e: Exception) {
                e.printStackTrace()
                val messageUtilisateur = when (e) {
                    is java.net.UnknownHostException -> "Connexion impossible. Vérifiez votre connexion Internet."
                    is java.net.SocketTimeoutException -> "Le serveur ne répond pas. Veuillez réessayer plus tard."
                    else -> "Une erreur s’est produite. Veuillez réessayer plus tard."
                }
                _uiState.value = UiState.Error(messageUtilisateur)

            }
        }
    }
    */
}