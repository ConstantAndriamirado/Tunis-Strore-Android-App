package tunisStore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tunisStore.app.ui.screens.BienvenueScreen
import tunisStore.app.ui.theme.TunisStoreTheme

class Bienvenue : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunisStoreTheme {
                BienvenueScreen()
            }
        }
    }
}