package tunisStore.app.ui.components

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import tunisStore.app.ui.data.AppData
import tunisStore.app.ui.theme.OrangePrimary
import tunisStore.app.AchatActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Locale

@Composable
fun AppDetailsModal(
    app: AppData,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showConfirmPurchaseModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(enabled = true, onClick = onClose)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // Full height to cover bottom navigation
                .align(Alignment.Center)
                .clickable(enabled = false) {}
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Fermer", tint = Color(0xFF333333))
                    }
                    Text(
                        text = "Détail de l'application",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color(0xFF333333))
                    }
                }

                // Info principale
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = app.thumbnailRes),
                        contentDescription = "Icône de ${app.name}",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column {
                        Text(app.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                        Text("Développeur : ${app.developer ?: "Inconnu"}", color = Color(0xFF666666))
                        Text(
                            text = String.format(Locale.FRENCH, "Prix : %.2f DN", app.price), // Formatage du prix
                            color = OrangePrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Taille : ${app.size}", color = Color(0xFF666666))
                        Text("Note moyenne : ${app.rating} / 5", color = Color(0xFF333333), fontWeight = FontWeight.Bold)
                        Text("Nombre d'avis : ${app.rating.toInt()} commentaires", color = Color(0xFF333333))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bouton principal
                Button(
                    onClick = {
                        Toast.makeText(context, "Procéder à l'achat", Toast.LENGTH_SHORT).show()
                        showConfirmPurchaseModal = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = String.format(Locale.FRENCH, "Acheter - %.2f DN", app.price), // Formatage du prix
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Section Aperçu visuel
                Text("Aperçu visuel", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF333333))
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Use actual screenshots if available, fallback to thumbnail
                    if (app.screenshots.isNotEmpty()) {
                        app.screenshots.forEach { screenshotRes ->
                            Image(
                                painter = painterResource(id = screenshotRes),
                                contentDescription = "Capture d'écran de ${app.name}",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            )
                        }
                    } else {
                        // Fallback to thumbnail if no screenshots
                        Image(
                            painter = painterResource(id = app.thumbnailRes),
                            contentDescription = "Icône de ${app.name}",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // À propos
                Text("À propos de cette application", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF333333))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = app.description ?: "Aucune description disponible",
                    color = Color(0xFF666666),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Dev info
                Text("Développeur & Support", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF333333))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Email : crafftsifir@gmail.com", color = Color.Blue, modifier = Modifier.clickable { /* TODO: Open email */ })
                Text("Site : www.crafftsifir.mg", color = Color.Blue, modifier = Modifier.clickable { /* TODO: Open website */ })
                Text("Politique de confidentialité", color = Color.Blue, modifier = Modifier.clickable { /* TODO: Open privacy policy */ })
            }
        }

        if (showConfirmPurchaseModal) {
            ConfirmPurchaseModal(
                app = app,
                onClose = { showConfirmPurchaseModal = false },
                onConfirm = { phoneNumber, paymentMethod ->
                    Toast.makeText(context, "Paiement: $paymentMethod, $phoneNumber", Toast.LENGTH_SHORT).show()
                    // Navigate to AchatActivity after payment confirmation
                    val intent = Intent(context, AchatActivity::class.java).apply {
                        Log.d("ACHAT_DEBUG VENANT DE CONFIRM PURCHASE", "Nom de l'app : ${app.name}, thumbnailRes ID = ${app.thumbnailRes}")
                        putExtra("APP_DATA", Json.encodeToString(app))
                    }
                    context.startActivity(intent)
                    showConfirmPurchaseModal = false
                }
            )
        }
    }
}