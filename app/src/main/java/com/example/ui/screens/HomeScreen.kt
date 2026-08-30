package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.data.local.CategoryEntity
import com.example.data.local.ProductEntity
import com.example.ui.components.ProductCard
import com.example.ui.language.AppLanguage
import com.example.ui.language.AppStrings
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SapphireBlue
import com.example.ui.viewmodel.NasheedTrack
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    featuredProducts: List<ProductEntity>,
    categories: List<CategoryEntity>,
    currentLanguage: AppLanguage,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onCategoryClick: (String) -> Unit,
    onProductClick: (ProductEntity) -> Unit,
    onAddToCart: (ProductEntity) -> Unit,
    onViewAllProducts: () -> Unit,
    onCallClick: () -> Unit,
    onTelegramClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onMapClick: () -> Unit = {},
    onLikeClick: (ProductEntity) -> Unit = {},
    onHeartClick: (ProductEntity) -> Unit = {},
    onReviewClick: (ProductEntity) -> Unit = {},
    onOpenAiAdvisor: () -> Unit = {},
    onOpenCompare: () -> Unit = {},
    onToggleCompare: (ProductEntity) -> Unit = {},
    isProductCompared: (Int) -> Boolean = { false },
    nasheedTracks: List<NasheedTrack> = emptyList(),
    currentTrack: NasheedTrack? = null,
    isPlayingNasheed: Boolean = false,
    isNasheedExpanded: Boolean = false,
    onToggleNasheedSection: () -> Unit = {},
    onPlayNasheedTrack: (NasheedTrack) -> Unit = {},
    onTogglePlayPauseNasheed: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val heroBannerId = remember(context) {
        var id = context.resources.getIdentifier("img_east_turkestan_banner_1786190275040", "drawable", context.packageName)
        if (id == 0) {
            id = context.resources.getIdentifier("img_hero_banner_1786037578646", "drawable", context.packageName)
        }
        if (id != 0) id else android.R.drawable.ic_dialog_info
    }

    val phonesImgId = remember(context) {
        val id = context.resources.getIdentifier("img_phones_1786037591338", "drawable", context.packageName)
        if (id != 0) id else android.R.drawable.ic_menu_gallery
    }

    val tabletsImgId = remember(context) {
        val id = context.resources.getIdentifier("img_tablets_1786037603482", "drawable", context.packageName)
        if (id != 0) id else android.R.drawable.ic_menu_gallery
    }

    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_pull_to_refresh"),
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
                .verticalScroll(scrollState)
                .padding(bottom = 24.dp)
        ) {
        // Hero Banner Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(200.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = heroBannerId),
                    contentDescription = "Store Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Subtle gradient overlay (softened dark overlay so background image is bright and visible)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.15f),
                                    Color.Black.copy(alpha = 0.55f)
                                )
                            )
                        )
                )

                // Phone image on top left corner
                Image(
                    painter = painterResource(id = phonesImgId),
                    contentDescription = "Phones",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .size(width = 50.dp, height = 36.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Tablet image on top right corner
                Image(
                    painter = painterResource(id = tabletsImgId),
                    contentDescription = "Tablets",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(width = 50.dp, height = 36.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Headline Text
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = AppStrings.get("hero_high_tech", currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = AppStrings.get("hero_store_subtitle", currentLanguage),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = AppStrings.get("hero_features_list", currentLanguage),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }

                    // Bottom info: Badge + Uyghur/Translated Title & Subtitle
                    Column(
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Surface(
                            color = GoldPrimary,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = AppStrings.get("hero_official_badge", currentLanguage),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = AppStrings.get("app_title", currentLanguage),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.get("app_subtitle", currentLanguage),
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }



        // AI Shopping Advisor & Product Comparison Quick Action Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // AI Shopping Advisor Card
            Card(
                onClick = onOpenAiAdvisor,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .weight(1f)
                    .testTag("home_ai_advisor_card")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GoldPrimary.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🤖", fontSize = 18.sp)
                        }
                    }
                    Column {
                        Text(
                            text = AppStrings.get("ai_advisor", currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = AppStrings.get("ai_assistant_subtitle", currentLanguage),
                            fontSize = 9.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }

            // Product Comparison Card
            Card(
                onClick = onOpenCompare,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .weight(1f)
                    .testTag("home_compare_card")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GoldPrimary.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("⚖️", fontSize = 18.sp)
                        }
                    }
                    Column {
                        Text(
                            text = AppStrings.get("compare_products", currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = AppStrings.get("compare_desc", currentLanguage),
                            fontSize = 9.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories Section
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppStrings.get("categories", currentLanguage),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Nasheed Listen Button on the left side of Categories header
                Surface(
                    onClick = onToggleNasheedSection,
                    shape = RoundedCornerShape(20.dp),
                    color = if (isNasheedExpanded || isPlayingNasheed) GoldPrimary.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, if (isPlayingNasheed) GoldPrimary else Color.Transparent),
                    modifier = Modifier.testTag("nasheed_toggle_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlayingNasheed) Icons.Default.MusicNote else Icons.Default.Audiotrack,
                            contentDescription = "Nasheed",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = AppStrings.get("listen_nasheed", currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false,
                            color = if (isPlayingNasheed) GoldPrimary else MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            imageVector = if (isNasheedExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Expandable Nasheed List Section
            AnimatedVisibility(
                visible = isNasheedExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Header of playlist
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LibraryMusic,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = AppStrings.get("nasheed_playlist", currentLanguage),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            if (currentTrack != null) {
                                // Play / Pause Quick Control
                                IconButton(
                                    onClick = onTogglePlayPauseNasheed,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingNasheed) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                        contentDescription = "Play/Pause",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                        // Track List
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            nasheedTracks.forEach { track ->
                                val isSelected = currentTrack?.id == track.id
                                val isCurrentlyPlaying = isSelected && isPlayingNasheed

                                Surface(
                                    onClick = { onPlayNasheedTrack(track) },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) GoldPrimary else Color.Transparent
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                imageVector = if (isCurrentlyPlaying) Icons.Default.GraphicEq else if (isSelected) Icons.Default.PlayArrow else Icons.Default.MusicNote,
                                                contentDescription = null,
                                                tint = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = track.title,
                                                fontSize = 13.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        Text(
                                            text = if (isCurrentlyPlaying) "ئاڭلىنىۋاتىدۇ 🔊" else if (isSelected) "توقتاپ تۇردى ⏸️" else "ئاڭلاش ▶️",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { cat ->
                    val catName = when (currentLanguage) {
                        AppLanguage.UYGHUR -> AppStrings.get(cat.id, AppLanguage.UYGHUR).ifEmpty { cat.nameUg }
                        AppLanguage.ARABIC -> cat.nameAr
                        AppLanguage.ENGLISH -> cat.nameEn
                    }
                    val icon = when (cat.id) {
                        "phones" -> Icons.Default.PhoneIphone
                        "tablets" -> Icons.Default.TabletMac
                        "accessories" -> Icons.Default.Headphones
                        else -> Icons.Default.Watch
                    }

                    Card(
                        modifier = Modifier
                            .width(130.dp)
                            .clickable { onCategoryClick(cat.id) }
                            .testTag("cat_card_${cat.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = catName,
                                tint = GoldPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = catName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Featured Products Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppStrings.get("featured_products", currentLanguage),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            TextButton(
                onClick = onViewAllProducts,
                modifier = Modifier.testTag("view_all_products_btn")
            ) {
                Text(
                    text = AppStrings.get("products", currentLanguage) + " →",
                    color = GoldPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Featured Products Auto-Scrolling Carousel
        if (featuredProducts.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { featuredProducts.size })

            // Auto scroll every 3 seconds
            LaunchedEffect(pagerState, featuredProducts) {
                while (true) {
                    delay(3000L)
                    if (featuredProducts.isNotEmpty()) {
                        val nextPage = (pagerState.currentPage + 1) % featuredProducts.size
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("featured_products_carousel")
                ) { page ->
                    val product = featuredProducts[page]
                    ProductCard(
                        product = product,
                        currentLanguage = currentLanguage,
                        onProductClick = { onProductClick(product) },
                        onAddToCart = { onAddToCart(product) },
                        onLikeClick = { onLikeClick(product) },
                        onHeartClick = { onHeartClick(product) },
                        onReviewClick = { onReviewClick(product) },
                        onCompareClick = { onToggleCompare(product) },
                        isCompared = isProductCompared(product.id),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Carousel indicator dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(featuredProducts.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (isSelected) 10.dp else 6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                        )
                    }
                }
            }
        }
    }
}
}
