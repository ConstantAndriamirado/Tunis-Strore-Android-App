package tunisStore.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    secondary = RedColor,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun TunisStoreTheme(content: @Composable () -> Unit) {
    val AppShapes = Shapes(
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(24.dp)
    )

    val AppTypography = Typography(
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
    )


    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography, // fourni dans ton Typography.kt
        shapes = AppShapes,         // fourni dans ton Shape.kt
        content = content
    )
}
