package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class AppThemeMode(
    val id: String,
    val nameUg: String,
    val nameAr: String,
    val nameEn: String,
    val previewPrimary: Color,
    val previewSecondary: Color,
    val iconEmoji: String
) {
    SKY_BLUE(
        id = "sky_blue",
        nameUg = "كۆكتۇغ كۆك",
        nameAr = "الأزرق السماوي",
        nameEn = "Sky Blue",
        previewPrimary = Color(0xFF1E88E5),
        previewSecondary = Color(0xFF00A8CC),
        iconEmoji = "🌊"
    ),
    ROYAL_GOLD(
        id = "royal_gold",
        nameUg = "نۇرلۇق ئالتۇن",
        nameAr = "الذهب الملكي",
        nameEn = "Royal Gold",
        previewPrimary = Color(0xFFE5A93C),
        previewSecondary = Color(0xFF1E5B84),
        iconEmoji = "👑"
    ),
    EMERALD_GREEN(
        id = "emerald",
        nameUg = "زۇمرەت يېشىل",
        nameAr = "الزمرد الأخضر",
        nameEn = "Emerald Oasis",
        previewPrimary = Color(0xFF059669),
        previewSecondary = Color(0xFFF59E0B),
        iconEmoji = "🌿"
    ),
    MIDNIGHT_PURPLE(
        id = "midnight_purple",
        nameUg = "ئېسىل بىنەپشە",
        nameAr = "البنفسجي الفاخر",
        nameEn = "Midnight Amethyst",
        previewPrimary = Color(0xFF8B5CF6),
        previewSecondary = Color(0xFFEC4899),
        iconEmoji = "🔮"
    );

    fun getDisplayName(code: String): String = when (code) {
        "ug" -> nameUg
        "ar" -> nameAr
        else -> nameEn
    }
}

// 1. Sky Blue Color Schemes
private val SkyBlueLight = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD2ECFA),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF00A8CC),
    onSecondary = Color.White,
    tertiary = Color(0xFF00B4D8),
    background = Color(0xFFDDF0F9),
    onBackground = Color(0xFF0B2E46),
    surface = Color(0xFFEBF6FD),
    onSurface = Color(0xFF0B2E46),
    surfaceVariant = Color(0xFFD2E8F6),
    onSurfaceVariant = Color(0xFF336080)
)

private val SkyBlueDark = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF0D47A1),
    primaryContainer = Color(0xFF1565C0),
    onPrimaryContainer = Color(0xFFD2ECFA),
    secondary = Color(0xFF00A8CC),
    onSecondary = Color.Black,
    tertiary = Color(0xFF80DEEA),
    background = Color(0xFF0B1E2C),
    onBackground = Color(0xFFE2F3FD),
    surface = Color(0xFF132F45),
    onSurface = Color(0xFFE2F3FD),
    surfaceVariant = Color(0xFF1E425E),
    onSurfaceVariant = Color(0xFF91C4E5)
)

// 2. Royal Gold Color Schemes
private val RoyalGoldLight = lightColorScheme(
    primary = Color(0xFFD49A00),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFDF3D6),
    onPrimaryContainer = Color(0xFF6B4E00),
    secondary = Color(0xFF1E5B84),
    onSecondary = Color.White,
    tertiary = Color(0xFFB8860B),
    background = Color(0xFFFAF7EE),
    onBackground = Color(0xFF2C2411),
    surface = Color(0xFFFFFDF5),
    onSurface = Color(0xFF2C2411),
    surfaceVariant = Color(0xFFF4ECDB),
    onSurfaceVariant = Color(0xFF6B5C3D)
)

private val RoyalGoldDark = darkColorScheme(
    primary = Color(0xFFFFD54F),
    onPrimary = Color(0xFF4D3800),
    primaryContainer = Color(0xFF7A5800),
    onPrimaryContainer = Color(0xFFFFE082),
    secondary = Color(0xFF64B5F6),
    onSecondary = Color.Black,
    tertiary = Color(0xFFFFCA28),
    background = Color(0xFF1C1914),
    onBackground = Color(0xFFFDF6E3),
    surface = Color(0xFF2A241B),
    onSurface = Color(0xFFFDF6E3),
    surfaceVariant = Color(0xFF3D3425),
    onSurfaceVariant = Color(0xFFD1C4A5)
)

// 3. Emerald Oasis Color Schemes
private val EmeraldLight = lightColorScheme(
    primary = Color(0xFF059669),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1FAE5),
    onPrimaryContainer = Color(0xFF064E3B),
    secondary = Color(0xFFF59E0B),
    onSecondary = Color.White,
    tertiary = Color(0xFF10B981),
    background = Color(0xFFF0FDF4),
    onBackground = Color(0xFF062C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF062C1E),
    surfaceVariant = Color(0xFFDCFCE7),
    onSurfaceVariant = Color(0xFF166534)
)

private val EmeraldDark = darkColorScheme(
    primary = Color(0xFF6EE7B7),
    onPrimary = Color(0xFF064E3B),
    primaryContainer = Color(0xFF047857),
    onPrimaryContainer = Color(0xFFA7F3D0),
    secondary = Color(0xFFFBBF24),
    onSecondary = Color.Black,
    tertiary = Color(0xFF34D399),
    background = Color(0xFF062117),
    onBackground = Color(0xFFECFDF5),
    surface = Color(0xFF0D3526),
    onSurface = Color(0xFFECFDF5),
    surfaceVariant = Color(0xFF154D38),
    onSurfaceVariant = Color(0xFFA7F3D0)
)

// 4. Midnight Amethyst Color Schemes
private val AmethystLight = lightColorScheme(
    primary = Color(0xFF7C3AED),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = Color(0xFF4C1D95),
    secondary = Color(0xFFEC4899),
    onSecondary = Color.White,
    tertiary = Color(0xFF8B5CF6),
    background = Color(0xFFFAF5FF),
    onBackground = Color(0xFF2E1065),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF2E1065),
    surfaceVariant = Color(0xFFF3E8FF),
    onSurfaceVariant = Color(0xFF6B21A8)
)

private val AmethystDark = darkColorScheme(
    primary = Color(0xFFC4B5FD),
    onPrimary = Color(0xFF4C1D95),
    primaryContainer = Color(0xFF6D28D9),
    onPrimaryContainer = Color(0xFFEDE9FE),
    secondary = Color(0xFFF472B6),
    onSecondary = Color.Black,
    tertiary = Color(0xFFA78BFA),
    background = Color(0xFF140C24),
    onBackground = Color(0xFFFAF5FF),
    surface = Color(0xFF22143D),
    onSurface = Color(0xFFFAF5FF),
    surfaceVariant = Color(0xFF321E59),
    onSurfaceVariant = Color(0xFFDDD6FE)
)

val LocalAppThemeMode = staticCompositionLocalOf { AppThemeMode.SKY_BLUE }

@Composable
fun NoorStoreTheme(
    appTheme: AppThemeMode = AppThemeMode.SKY_BLUE,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (appTheme) {
        AppThemeMode.SKY_BLUE -> if (darkTheme) SkyBlueDark else SkyBlueLight
        AppThemeMode.ROYAL_GOLD -> if (darkTheme) RoyalGoldDark else RoyalGoldLight
        AppThemeMode.EMERALD_GREEN -> if (darkTheme) EmeraldDark else EmeraldLight
        AppThemeMode.MIDNIGHT_PURPLE -> if (darkTheme) AmethystDark else AmethystLight
    }

    CompositionLocalProvider(LocalAppThemeMode provides appTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
