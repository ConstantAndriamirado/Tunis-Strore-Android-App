package tunisStore.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.R
import tunisStore.app.ui.data.AppData
import java.util.Locale
import kotlin.random.Random

@Composable
fun SearchResultCard(app: AppData, onAppClick: (AppData) -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = app.thumbnailRes),
                contentDescription = app.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(app.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(app.category, color = Color.Gray, fontSize = 13.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(app.rating.toString(), fontSize = 13.sp)
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = String.format(Locale.FRENCH, "%.2f DN", app.price), // Formatage du prix
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onAppClick(app) },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Acheter", // Toutes les apps sont payantes
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}



@Composable
fun RechercheScreenContent() {
    val searchQuery = remember { mutableStateOf("") }
    var selectedApp by remember { mutableStateOf<AppData?>(null) }

    fun generateRandomPrice(): Float {
        return (Random.nextFloat() * (99.99f - 0.99f) + 0.99f).coerceAtMost(99.99f)
    }

    val searchResults = listOf(
        AppData("Photoshop", "Photo & Vidéo", 4.7, "250 Mo", 89.1f, R.drawable.ic_adobe_photoshop), // Changé de "89,1 DN"
        AppData("Scanner", "Éducation", 5.0, "12 Mo", generateRandomPrice(), R.drawable.ic_scanner), // Changé de "Gratuits"
        AppData("Excel", "Productivité", 4.9, "90 Mo", 9.1f, R.drawable.ic_microsoft_excel), // Changé de "9,1 DN"
        AppData("Instagramm", "Réseaux-sociaux", 4.6, "120 Mo", generateRandomPrice(), R.drawable.ic_instagram), // Changé de "Achat Intégrée"
        AppData("Anglais Vocab", "Éducation", 4.4, "60 Mo", 19.1f, R.drawable.ic_great_britain), // Changé de "19,1 DN"
        AppData("Twitter", "Réseaux-sociaux", 4.8, "110 Mo", generateRandomPrice(), R.drawable.ic_twitter) // Changé de "Gratuits"
    )

    // Filtrer les résultats basés sur la recherche
    val filteredResults = remember(searchQuery.value) {
        if (searchQuery.value.isBlank()) {
            searchResults
        } else {
            searchResults.filter { app ->
                app.name.contains(searchQuery.value, ignoreCase = true) ||
                        app.category.contains(searchQuery.value, ignoreCase = true)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Trouvez l'appli parfaite pour vous, en quelques secondes.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                placeholder = { Text("Rechercher une application") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Résultats (${filteredResults.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredResults) { app ->
                    SearchResultCard(
                        app = app,
                        onAppClick = { selectedApp = it }
                    )
                }

                if (filteredResults.isEmpty() && searchQuery.value.isNotBlank()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Aucun résultat trouvé",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Essayez avec d'autres mots-clés",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Modal pour les détails de l'app (si vous avez le composant)
        selectedApp?.let { app ->
            // AppDetailsModal(app = app, onClose = { selectedApp = null })
            // Ou une simple alerte pour l'instant :
            AlertDialog(
                onDismissRequest = { selectedApp = null },
                title = { Text(app.name) },
                text = {
                    Column {
                        Text("Catégorie: ${app.category}")
                        Text("Note: ${app.rating}")
                        Text("Taille: ${app.size}")
                        Text("Prix: ${app.price}")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedApp = null }) {
                        Text("Fermer")
                    }
                }
            )
        }
    }
}

// Gardez l'ancienne version pour compatibilité si nécessaire
@Composable
fun RechercheScreen() {
    // Cette version utilise encore Scaffold - à supprimer une fois la migration terminée
    RechercheScreenContent()
}