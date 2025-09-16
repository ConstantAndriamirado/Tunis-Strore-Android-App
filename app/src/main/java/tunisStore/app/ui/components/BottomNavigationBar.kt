// BottomNavigationBar.kt - Version corrigée
package tunisStore.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tunisStore.app.ui.theme.OrangePrimary

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(containerColor = OrangePrimary) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Accueil",
                    tint = if (selectedTab == "accueil") OrangePrimary else Color.Black
                )
            },
            label = { Text("Accueil", color = Color.Black) },
            selected = selectedTab == "accueil",
            onClick = { onTabSelected("accueil") }, // ✅ Navigation Compose
            modifier = if (selectedTab == "accueil") Modifier.background(OrangePrimary.copy(alpha = 0.2f)) else Modifier
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = "Catégorie",
                    tint = if (selectedTab == "categorie") OrangePrimary else Color.Black
                )
            },
            label = { Text("Catégorie", color = Color.Black) },
            selected = selectedTab == "categorie",
            onClick = { onTabSelected("categorie") }, // ✅ Navigation Compose
            modifier = if (selectedTab == "categorie") Modifier.background(OrangePrimary.copy(alpha = 0.2f)) else Modifier
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Achat",
                    tint = if (selectedTab == "achat") OrangePrimary else Color.Black
                )
            },
            label = { Text("Achat", color = Color.Black) },
            selected = selectedTab == "achat",
            onClick = { onTabSelected("achat") }, // ✅ Navigation Compose
            modifier = if (selectedTab == "achat") Modifier.background(OrangePrimary.copy(alpha = 0.2f)) else Modifier
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Recherche",
                    tint = if (selectedTab == "recherche") OrangePrimary else Color.Black
                )
            },
            label = { Text("Recherche", color = Color.Black) },
            selected = selectedTab == "recherche",
            onClick = { onTabSelected("recherche") }, // ✅ Navigation Compose
            modifier = if (selectedTab == "recherche") Modifier.background(OrangePrimary.copy(alpha = 0.2f)) else Modifier
        )
    }
}