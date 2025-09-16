package tunisStore.app.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tunisStore.app.ui.components.BottomNavigationBar
import tunisStore.app.ui.components.Header
import tunisStore.app.ui.data.AppData
import kotlinx.serialization.json.Json
import tunisStore.app.AchatActivity
import tunisStore.app.R
import java.text.SimpleDateFormat
import java.util.*
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun AchatScreen() {
    val context = LocalContext.current
    val intent = (context as? AchatActivity)?.intent
    val newAppJson = intent?.getStringExtra("APP_DATA")
    val newApp = newAppJson?.let { Json.decodeFromString<AppData>(it) }


    val achatList = remember { mutableStateListOf<AchatApp>().apply { addAll(fakeAchats) } }

    LaunchedEffect(newApp) {
        newApp?.let { app ->
            val currentDate = SimpleDateFormat("dd MMM yyyy à HH:mm", Locale.FRENCH).format(Date())

            val iconRes = if (app.thumbnailRes != 0) app.thumbnailRes else R.drawable.ic_default_app

            val newAchat = AchatApp(
                name = app.name,
                category = app.category,
                date = currentDate,
                price = app.price,
                thumbnailRes = iconRes,
                statusIcon = Icons.Default.PlayArrow,
                statusColor = Color.DarkGray,
                statusText = "Téléchargement en attente..."
            )

            achatList.add(newAchat)

            val apkUrl = "https://github.com/ConstantAndriamirado/stockage/raw/main/jeu.apk"
            downloadAndInstallApk(context, apkUrl, "JeuTest")


            achatList[achatList.indexOf(newAchat)] = newAchat.copy(
                statusIcon = Icons.Default.Download,
                statusColor = Color(0xFF008000),
                statusText = "Téléchargement lancé"
            )

        }
    }


    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Voici toutes les applications que vous avez achetées. Cliquez pour les télécharger à nouveau.",
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Liste des applications achetées",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(achatList.size) { index ->
                AchatCard(achatList[index])
            }
        }
    }
}

@Composable
fun AchatCard(app: AchatApp) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = app.thumbnailRes),
                contentDescription = "Icône ${app.name}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(app.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(app.category, fontSize = 14.sp, color = Color(0xFF696969))
                Text("Date d'achat : ${app.date}", fontSize = 13.sp)
                Text(
                    text = String.format(Locale.FRENCH, "Prix payé : %.2f DN", app.price),
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = app.statusIcon,
                        contentDescription = null,
                        tint = app.statusColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = app.statusText,
                        fontSize = 11.sp,
                        color = app.statusColor
                    )
                }
            }
        }
    }
}

data class AchatApp(
    val name: String,
    val category: String,
    val date: String,
    val price: Float,
    val thumbnailRes: Int,
    val statusIcon: ImageVector,
    val statusColor: Color,
    val statusText: String
)


fun downloadAndInstallApk(context: Context, apkUrl: String, appName: String) {
    val uri = Uri.parse(apkUrl)
    val request = DownloadManager.Request(uri)
    request.setTitle("Téléchargement de $appName")
    request.setDescription("Téléchargement en cours...")
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$appName.apk")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setMimeType("application/vnd.android.package-archive")
    request.allowScanningByMediaScanner()

    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadId = manager.enqueue(request)

    val onComplete = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val uri: Uri = manager.getUriForDownloadedFile(downloadId) ?: return
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$appName.apk")

                val apkUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    file
                )

                val installIntent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(apkUri, "application/vnd.android.package-archive")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                }

                context.startActivity(installIntent)
            }
        }
    }

    val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)

    ContextCompat.registerReceiver(
        context,
        onComplete,
        filter,
        ContextCompat.RECEIVER_NOT_EXPORTED
    )

    Toast.makeText(context, "Téléchargement de $appName démarré", Toast.LENGTH_SHORT).show()
}


val fakeAchats = listOf(
    AchatApp(
        name = "Photoshop",
        category = "Photo & Vidéo",
        date = "29 Mai 2025 à 22:57",
        price = 89.57f,
        thumbnailRes = R.drawable.ic_adobe_photoshop,
        statusIcon = Icons.Default.Pause,
        statusColor = Color.DarkGray,
        statusText = "Téléchargement : 12 Mo / 150,5 Mo • 200 Ko/s • 6 minutes restantes"
    ),
    AchatApp(
        name = "Excel",
        category = "Business",
        date = "29 Mai 2025 à 22:57",
        price = 19.1f,
        thumbnailRes = R.drawable.ic_microsoft_excel,
        statusIcon = Icons.Default.PlayArrow,
        statusColor = Color.DarkGray,
        statusText = "Téléchargement en attente..."
    ),
    AchatApp(
        name = "Trader",
        category = "Business",
        date = "27 Mai 2025 à 14:21",
        price = 56.10f,
        thumbnailRes = R.drawable.ic_education,
        statusIcon = Icons.Default.Download,
        statusColor = Color(0xFF008000),
        statusText = "Téléchargé"
    )
)