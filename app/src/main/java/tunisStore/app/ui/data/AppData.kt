package tunisStore.app.ui.data

import kotlinx.serialization.Serializable
import tunisStore.app.R
import java.util.Collections.emptyList
import kotlin.collections.*
import kotlin.random.Random

@Serializable
data class AppData(
    val name: String,
    val category: String,
    val rating: Double,
    val size: String,
    val price: Float,
    val thumbnailRes: Int = 0,
    val developer: String? = null,
    val description: String? = null,
    val screenshots: List<Int> = emptyList()
)

@Serializable
data class AppSectionData(
    val title: String,
    val apps: List<AppData>
)

fun generateRandomPrice(): Float {
    return (Random.nextFloat() * (99.99f - 0.99f) + 0.99f).coerceAtMost(99.99f)
}

val fakeAppSections = listOf(
    AppSectionData(
        "Recommandé pour vous",
        listOf(
            AppData("Dream", "Réseaux sociaux", 4.8, "111,5 Mo", 6.99f, R.drawable.ic_dream),
            AppData("Rally 214", "Jeux", 4.2, "500 Mo", 9.99f, R.drawable.ic_rally),
            AppData("Read-Book", "Éducation", 4.9, "21,2 Mo", 89.99f, R.drawable.ic_book)
        )
    ),
    AppSectionData(
        "Populaire",
        listOf(
            AppData("Instagram", "Réseaux sociaux", 4.6, "120 Mo", 79.99f, R.drawable.ic_instagram),
            AppData("WhatsApp", "Réseaux sociaux", 4.7, "100 Mo", 59.99f, R.drawable.ic_whatsapp),
            AppData("Photoshop", "Photo & Vidéo", 4.5, "1,5 Go", 89.99f, R.drawable.ic_adobe_photoshop)
        )
    ),
    AppSectionData(
        "Nouveautés",
        listOf(
            AppData("Call OF D", "Jeux", 4.0, "1,2 Go", 19.9f, R.drawable.ic_call_of_duty),
            AppData("DES", "Jeux", 3.9, "500 Mo", 14.9f, R.drawable.ic_des)
        )
    ),
    AppSectionData(
        "Promotions",
        listOf(
            AppData("Dream", "Réseaux sociaux", 4.8, "111,5 Mo", 19.99f, R.drawable.ic_dream), // "Gratuits" remplacé
            AppData("Rally 214", "Jeux", 4.2, "500 Mo", 4.9f, R.drawable.ic_rally)
        )
    )
)