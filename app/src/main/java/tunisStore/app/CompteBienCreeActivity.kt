package tunisStore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tunisStore.app.ui.screens.CompteBienCreeScreen
import tunisStore.app.ui.theme.TunisStoreTheme

class CompteBienCreeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunisStoreTheme {
                CompteBienCreeScreen()
            }
        }
    }
}