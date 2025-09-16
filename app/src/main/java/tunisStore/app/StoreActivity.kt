package tunisStore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tunisStore.app.ui.components.BottomNavigationBar
import tunisStore.app.ui.components.Header
import tunisStore.app.ui.screens.*
import tunisStore.app.ui.theme.TunisStoreTheme
import tunisStore.app.ui.viewmodels.AccueilViewModel
import tunisStore.app.ui.viewmodels.UiState
import tunisStore.app.ui.data.AppData
import tunisStore.app.ui.components.AppDetailsModal

class StoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunisStoreTheme {
                TunisStoreApp()
            }
        }
    }
}

@Composable
fun TunisStoreApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "accueil"

    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = currentRoute,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "accueil",
            modifier = Modifier.padding(padding)
        ) {
            composable("accueil") {
                AccueilScreenContent()
            }
            composable("achat") {
                AchatScreenContent()
            }
            composable("categorie") {
                CategorieScreenContent()
            }
            composable("recherche") {
                RechercheScreenContent()
            }
        }
    }
}


@Composable
fun AccueilScreenContent(viewModel: AccueilViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedApp by remember { mutableStateOf<AppData?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .fillMaxSize()
        ) {
            WelcomeBanner()

            when (uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    (uiState as UiState.Success).sections.forEach { section ->
                        AppSection(section, onAppClick = { selectedApp = it })
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as UiState.Error).message,
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        if (selectedApp != null) {
            AppDetailsModal(app = selectedApp!!, onClose = { selectedApp = null })
        }
    }
}

// AchatScreen adapté - SANS Scaffold
@Composable
fun AchatScreenContent() {
   AchatScreen()
}

@Composable
fun CategorieScreenContent() {
    CategoriesScreen()
}

// RechercheScreen adapté - SANS Scaffold
@Composable
fun RechercheScreenContent() {
    RechercheScreen()
}