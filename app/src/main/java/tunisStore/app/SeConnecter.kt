package tunisStore.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.ui.theme.OrangePrimary
import tunisStore.app.ui.theme.RedColor

class SeConnecter : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeConnecterScreen()
        }
    }
}

@Composable
fun SeConnecterScreen() {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("tunis_store_prefs", Context.MODE_PRIVATE)

    // Charger les données sauvegardées
    var email by remember { mutableStateOf(preferences.getString("email", "") ?: "") }
    var password by remember { mutableStateOf(preferences.getString("password", "") ?: "") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(preferences.getBoolean("remember_me", false)) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = OrangePrimary,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Column {
                Text(
                    text = "< Retour",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, Bienvenue::class.java))
                        }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Se connecter",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }
        }

        // Corps du formulaire
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Adresse e-mail ou numéro de téléphone", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("exemple@mail.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Text("Mot de passe", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                placeholder = { Text("Mot de passe") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Afficher le mot de passe"
                        )
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text("Se souvenir de moi")
            }

            errorMessage?.let {
                Text(it, color = RedColor, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
            }

            Button(
                onClick = {
                    // Validation
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Tous les champs sont obligatoires."
                        return@Button
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Veuillez entrer une adresse e-mail valide."
                        return@Button
                    }

                    // Sauvegarder les informations si "Se souvenir de moi" est coché
                    with(preferences.edit()) {
                        if (rememberMe) {
                            putString("email", email)
                            putString("password", password)
                            putBoolean("remember_me", true)
                        } else {
                            remove("email")
                            remove("password")
                            putBoolean("remember_me", false)
                        }
                        apply()
                    }

                    // Success
                    errorMessage = null
                    context.startActivity(Intent(context, StoreActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text("Se connecter", color = Color.White)
            }

            OutlinedButton(
                onClick = {
                    // TODO: Connexion Google
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Se connecter avec Google")
            }

            Text(
                "Vous n'avez pas de compte Tunis Store ?",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp
            )

            Text(
                text = "Inscrivez-vous",
                color = RedColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        context.startActivity(Intent(context, CreationCompteActivity::class.java))
                    },
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = "Mot de passe oublié ?",
                color = RedColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable {
                        // TODO: Rediriger vers Mot de passe oublié
                    },
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}