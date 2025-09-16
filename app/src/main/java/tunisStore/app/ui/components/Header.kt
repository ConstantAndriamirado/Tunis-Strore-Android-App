package tunisStore.app.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.R
import tunisStore.app.StoreActivity
import tunisStore.app.ui.screens.BienvenueScreen
import tunisStore.app.ui.theme.OrangePrimary
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpOffset
import tunisStore.app.Bienvenue

@Composable
fun Header() {
    val context = LocalContext.current
    var showNotificationsMenu by remember { mutableStateOf(false) }
    var showUserMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    context.startActivity(Intent(context, StoreActivity::class.java))
                }
        )
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { showNotificationsMenu = !showNotificationsMenu }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Profile",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { showUserMenu = !showUserMenu }
            )
        }
    }

    // Notifications Dropdown Menu
    DropdownMenu(
        expanded = showNotificationsMenu,
        onDismissRequest = { showNotificationsMenu = false },
        modifier = Modifier
            .width(300.dp)
            .heightIn(max = 400.dp)
            .background(Color.White),
        offset = DpOffset(
            x = (-20).dp, // Align to the right edge with padding
            y = 64.dp // Just below the header (16.dp padding + 32.dp icon + 16.dp padding)
        )
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Rechercher notifications...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = OrangePrimary,
                unfocusedIndicatorColor = Color.Gray
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
        )

        // Scrollable notifications list
        val notifications = fakeNotifications
            .filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.message.contains(searchQuery, ignoreCase = true)
            }
            .sortedByDescending { it.date }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp) // Constrain height to avoid overflow
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            notifications.forEach { notification ->
                NotificationItem(notification)
                Divider(color = Color.Gray.copy(alpha = 0.2f))
            }
        }
    }

    // User Dropdown Menu
    DropdownMenu(
        expanded = showUserMenu,
        onDismissRequest = { showUserMenu = false },
        modifier = Modifier
            .width(200.dp)
            .background(Color.White),
        offset = DpOffset(
            x = (-20).dp, // Align to the right edge with padding
            y = 64.dp // Just below the header (16.dp padding + 32.dp icon + 16.dp padding)
        )
    ) {
        DropdownMenuItem(
            text = { Text("Gérer mon compte", fontSize = 14.sp) },
            onClick = {
                Toast.makeText(context, "Gérer mon compte", Toast.LENGTH_SHORT).show()
                showUserMenu = false
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = OrangePrimary
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenuItem(
            text = { Text("Déconnexion", fontSize = 14.sp) },
            onClick = {
                // Supprimer les informations de connexion
                with(context.getSharedPreferences("tunis_store_prefs", Context.MODE_PRIVATE).edit()) {
                    remove("email")
                    remove("password")
                    remove("remember_me")
                    apply()
                }
                Toast.makeText(context, "Déconnexion réussie", Toast.LENGTH_SHORT).show()
                showUserMenu = false
                // Rediriger vers BienvenueScreen
                context.startActivity(Intent(context, Bienvenue::class.java))
                // Fermer l'activité actuelle
                (context as? StoreActivity)?.finish()
            },
            leadingIcon = {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = OrangePrimary
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // TODO: Handle notification click
            }
    ) {
        Text(
            text = notification.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )
        Text(
            text = notification.message,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.FRENCH).format(notification.date),
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

data class Notification(
    val title: String,
    val message: String,
    val date: Date
)

val fakeNotifications = listOf(
    Notification(
        title = "Nouvelle mise à jour",
        message = "Photoshop a une nouvelle version disponible.",
        date = Date(System.currentTimeMillis() - 1 * 60 * 60 * 1000) // 1 hour ago
    ),
    Notification(
        title = "Paiement confirmé",
        message = "Votre achat de Minecraft a été confirmé.",
        date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000) // 1 day ago
    ),
    Notification(
        title = "Offre spéciale",
        message = "50% de réduction sur Twitter Premium.",
        date = Date(System.currentTimeMillis() - 48 * 60 * 60 * 1000) // 2 days ago
    )
)