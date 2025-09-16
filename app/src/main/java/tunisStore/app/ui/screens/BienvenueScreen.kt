package tunisStore.app.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.R
import tunisStore.app.SeConnecter
import tunisStore.app.CreationCompteActivity
import tunisStore.app.StoreActivity
import tunisStore.app.ui.theme.OrangePrimary
import androidx.compose.ui.graphics.Color

@Composable
fun BienvenueScreen() {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("tunis_store_prefs", Context.MODE_PRIVATE)

    // Vérifier si l'utilisateur est souvenu et rediriger immédiatement
    LaunchedEffect(Unit) {
        val rememberMe = preferences.getBoolean("remember_me", false)
        val email = preferences.getString("email", "") ?: ""
        val password = preferences.getString("password", "") ?: ""

        if (rememberMe && email.isNotBlank() && password.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.startActivity(Intent(context, StoreActivity::class.java))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            // Logo + Nom de l'app
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tounsi Store",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bienvenue sur Tounsi Store
            Row {
                Text(
                    text = "Bienvenue sur ",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Tounsi Store",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texte explicatif
            Text(
                text = "Des apps officielles, un paiement 100% local, une expérience personnalisée.\n" +
                        "Payez en dinars tunisiens, installez en un clic. Une fois l’achat effectué, " +
                        "votre accès est personnel et fonctionne sur tous vos appareils et les stores officiels comme le Play Store de votre smart TV.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Bouton Se connecter
            Button(
                onClick = {
                    context.startActivity(Intent(context, SeConnecter::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Se connecter",
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    context.startActivity(Intent(context, CreationCompteActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = OrangePrimary
                ),
                border = BorderStroke(1.dp, OrangePrimary)
            ) {
                Text(
                    text = "Créer un compte",
                    fontSize = 16.sp
                )
            }
        }
    }
}
