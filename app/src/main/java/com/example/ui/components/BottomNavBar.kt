package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.language.AppLanguage
import com.example.ui.language.AppStrings
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.Screen

@Composable
fun BottomNavBar(
    currentScreen: Screen,
    currentLanguage: AppLanguage,
    onScreenSelect: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBarItem(
            selected = currentScreen == Screen.HOME,
            onClick = { onScreenSelect(Screen.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = {
                Text(
                    text = AppStrings.get("home", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == Screen.HOME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_home")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.PRODUCTS,
            onClick = { onScreenSelect(Screen.PRODUCTS) },
            icon = { Icon(Icons.Default.Storefront, contentDescription = "Products") },
            label = {
                Text(
                    text = AppStrings.get("products", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == Screen.PRODUCTS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_products")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.FAVORITES,
            onClick = { onScreenSelect(Screen.FAVORITES) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = {
                Text(
                    text = AppStrings.get("favorites", currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentScreen == Screen.FAVORITES) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_favorites")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.CART,
            onClick = { onScreenSelect(Screen.CART) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = {
                Text(
                    text = AppStrings.get("cart", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == Screen.CART) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_cart")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.CONTACT,
            onClick = { onScreenSelect(Screen.CONTACT) },
            icon = { Icon(Icons.Default.ContactPhone, contentDescription = "Contact") },
            label = {
                Text(
                    text = AppStrings.get("contact", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == Screen.CONTACT) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_contact")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.ADMIN,
            onClick = { onScreenSelect(Screen.ADMIN) },
            icon = { Icon(Icons.Default.Security, contentDescription = "Admin") },
            label = {
                Text(
                    text = AppStrings.get("admin", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == Screen.ADMIN) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("nav_admin")
        )
    }
}
