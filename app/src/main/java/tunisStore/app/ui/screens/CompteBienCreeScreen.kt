package tunisStore.app.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import tunisStore.app.R
import android.content.Intent
import tunisStore.app.ValidationChoixPreferenceActivity

@Composable
fun CompteBienCreeScreen() {
    val context = LocalContext.current

    // Barre de progression animée
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 5000),
        label = "Progress Animation"
    )

    // Lancer l’animation une seule fois et changer d'écran après 5 sec
    LaunchedEffect(Unit) {
        progress = 1f
        Handler(Looper.getMainLooper()).postDelayed({
            context.startActivity(Intent(context, ValidationChoixPreferenceActivity::class.java))
        }, 5000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF5722), Color(0xFFFF7043)) // gradient simulant bg_gradient_orange_red
                )
            )
            .padding(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_approval),
                contentDescription = "Validation réussie",
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "Marhbè bik !",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )

            Text(
                text = "Votre compte est créé. Profitez maintenant d'un Apk Store 100% tunisien avec des paiements simples et rapides en dinars.",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Barre de progression
            LinearProgressIndicator(
                progress = animatedProgress.value,
                modifier = Modifier
                    .width(250.dp)
                    .height(10.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}
