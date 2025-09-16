package tunisStore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tunisStore.app.ui.screens.CreationCompteScreen
import tunisStore.app.ui.theme.TunisStoreTheme

class CreationCompteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunisStoreTheme {
                CreationCompteScreen()
            }
        }
    }
}