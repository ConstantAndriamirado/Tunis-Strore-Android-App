package tunisStore.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tunisStore.app.ui.components.AppDetailsModal
import tunisStore.app.ui.data.AppSectionData
import tunisStore.app.ui.data.AppData
import tunisStore.app.ui.viewmodels.AccueilViewModel
import tunisStore.app.ui.viewmodels.UiState

@Composable
fun AccueilScreen(viewModel: AccueilViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedApp by remember { mutableStateOf<AppData?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .fillMaxSize()
        ) {
            WelcomeBanner()

            when (uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    (uiState as UiState.Success).sections.forEach { section ->
                        AppSection(section, onAppClick = { selectedApp = it })
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as UiState.Error).message,
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        if (selectedApp != null) {
            AppDetailsModal(app = selectedApp!!, onClose = { selectedApp = null })
        }
    }
}

@Composable
fun WelcomeBanner() {
    Text(
        buildAnnotatedString {
            append("Bienvenue dans ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                append("Tounsi Store")
            }
            append(" - Téléchargez vos apps en dinars !")
        },
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        lineHeight = 22.sp,
        color = Color.Black
    )
}


@Composable
fun AppSection(section: AppSectionData, onAppClick: (AppData) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = section.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Voir plus",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.clickable {  }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val paidApps = section.apps.filter { it.price > 0f }

        LazyRow(contentPadding = PaddingValues(end = 16.dp)) {
            items(paidApps) { app ->
                AppCard(app = app, onClick = { onAppClick(app) })
            }
        }
    }
}

@Composable
fun AppCard(app: AppData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(end = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color(0xFFFF4216))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = app.thumbnailRes),
                contentDescription = "Icône de l'application ${app.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(app.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, maxLines = 1)
            Text(app.category, fontSize = 14.sp, color = Color(0xFF696969))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = app.rating.toString(), fontSize = 14.sp)
                }
                Text(app.size, fontSize = 14.sp)
            }
        }
    }
}
