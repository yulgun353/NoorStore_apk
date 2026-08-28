package com.example.ui.language

import androidx.compose.ui.unit.LayoutDirection

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val layoutDirection: LayoutDirection
) {
    UYGHUR("ug", "ئۇيغۇرچە", LayoutDirection.Rtl),
    ARABIC("ar", "العربية", LayoutDirection.Rtl),
    ENGLISH("en", "English", LayoutDirection.Ltr)
}
