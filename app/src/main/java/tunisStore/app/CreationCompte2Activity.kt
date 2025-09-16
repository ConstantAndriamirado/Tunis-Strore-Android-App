package tunisStore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tunisStore.app.ui.screens.CreationCompte2Screen
import tunisStore.app.ui.theme.TunisStoreTheme

class CreationCompte2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunisStoreTheme {
                CreationCompte2Screen()
            }
        }
    }
}