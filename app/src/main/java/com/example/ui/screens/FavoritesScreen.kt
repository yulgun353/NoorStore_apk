package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ProductEntity
import com.example.ui.components.ProductCard
import com.example.ui.language.AppLanguage
import com.example.ui.language.AppStrings
import com.example.ui.theme.GoldPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteProducts: List<ProductEntity>,
    currentLanguage: AppLanguage,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onProductClick: (ProductEntity) -> Unit,
    onAddToCart: (ProductEntity) -> Unit,
    onLikeClick: (ProductEntity) -> Unit,
    onHeartClick: (ProductEntity) -> Unit,
    onReviewClick: (ProductEntity) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("favorites_pull_to_refresh"),
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                color = GoldPrimary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Title Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorites",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = AppStrings.get("favorites", currentLanguage),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Surface(
                    color = GoldPrimary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "${favoriteProducts.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (favoriteProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❤️", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = AppStrings.get("no_favorites_yet", currentLanguage),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    modifier = Modifier.weight(1f).testTag("favorites_grid")
                ) {
                    items(favoriteProducts) { product ->
                        ProductCard(
                            product = product,
                            currentLanguage = currentLanguage,
                            onProductClick = { onProductClick(product) },
                            onAddToCart = { onAddToCart(product) },
                            onLikeClick = { onLikeClick(product) },
                            onHeartClick = { onHeartClick(product) },
                            onReviewClick = { onReviewClick(product) }
                        )
                    }
                }
            }
        }
    }
}
