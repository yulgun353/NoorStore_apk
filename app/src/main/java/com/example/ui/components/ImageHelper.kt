package com.example.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.GoldPrimary
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.UUID

object ImageStorageHelper {
    /**
     * Converts a picked gallery Uri into a compressed, cloud-portable Base64 Data URI (data:image/jpeg;base64,...).
     * This ensures the image is saved directly in Supabase and instantly visible to all users on every device.
     */
    fun saveUriToAppStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) return null
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            if (originalBitmap == null) return null

            // Downscale to max 800x800 for quick network sync and crisp display
            val maxDimension = 800
            val width = originalBitmap.width
            val height = originalBitmap.height
            val scaledBitmap = if (width > maxDimension || height > maxDimension) {
                val ratio = width.toFloat() / height.toFloat()
                val targetW: Int
                val targetH: Int
                if (width > height) {
                    targetW = maxDimension
                    targetH = (maxDimension / ratio).toInt().coerceAtLeast(1)
                } else {
                    targetH = maxDimension
                    targetW = (maxDimension * ratio).toInt().coerceAtLeast(1)
                }
                Bitmap.createScaledBitmap(originalBitmap, targetW, targetH, true)
            } else {
                originalBitmap
            }

            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val bytes = outputStream.toByteArray()

            // Save a local cache file as well
            try {
                val imagesDir = File(context.filesDir, "product_images").apply {
                    if (!exists()) mkdirs()
                }
                val fileName = "prod_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg"
                val destFile = File(imagesDir, fileName)
                destFile.writeBytes(bytes)
            } catch (_: Exception) {}

            val base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)
            "data:image/jpeg;base64,$base64String"
        } catch (e: Exception) {
            android.util.Log.e("ImageStorageHelper", "Error processing image uri: ${e.message}", e)
            null
        }
    }
}

@Composable
fun ProductImageView(
    imageSource: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    val trimmed = imageSource.trim()

    // 1. Check if Base64 Data URL or raw Base64
    val isBase64 = remember(trimmed) {
        trimmed.startsWith("data:image/") || (trimmed.length > 100 && !trimmed.startsWith("http") && !trimmed.startsWith("/"))
    }

    val decodedBitmap = remember(trimmed, isBase64) {
        if (isBase64) {
            try {
                val base64Data = if (trimmed.contains("base64,")) {
                    trimmed.substringAfter("base64,")
                } else {
                    trimmed
                }
                val decodedBytes = Base64.decode(base64Data, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: Exception) {
                android.util.Log.e("ProductImageView", "Error decoding base64 image: ${e.message}")
                null
            }
        } else {
            null
        }
    }

    val isFile = remember(trimmed) {
        trimmed.startsWith("/") || trimmed.startsWith("file://")
    }

    val isWeb = remember(trimmed) {
        trimmed.startsWith("http://") || trimmed.startsWith("https://")
    }

    val drawableResId = remember(trimmed, isBase64, isFile, isWeb) {
        if (isBase64 || isFile || isWeb || trimmed.isBlank()) {
            0
        } else {
            val cleanName = trimmed.substringBeforeLast(".")
            context.resources.getIdentifier(cleanName, "drawable", context.packageName)
        }
    }

    when {
        // A. Base64 decoded bitmap
        decodedBitmap != null -> {
            Image(
                bitmap = decodedBitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        // B. App bundled drawable resource (e.g. img_phones_...)
        drawableResId != 0 -> {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        // C. Web URL (e.g. https://...)
        isWeb -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(trimmed)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                error = painterResource(id = android.R.drawable.ic_menu_gallery)
            )
        }
        // D. Local device file path
        isFile -> {
            val file = remember(trimmed) {
                File(trimmed.removePrefix("file://"))
            }
            if (file.exists()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(file)
                        .crossfade(true)
                        .build(),
                    contentDescription = contentDescription,
                    modifier = modifier,
                    contentScale = contentScale,
                    error = painterResource(id = android.R.drawable.ic_menu_gallery)
                )
            } else {
                FallbackPlaceholder(modifier)
            }
        }
        // E. General string resource or fallback
        trimmed.isNotBlank() -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(trimmed)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                error = painterResource(id = android.R.drawable.ic_menu_gallery)
            )
        }
        else -> {
            FallbackPlaceholder(modifier)
        }
    }
}

@Composable
private fun FallbackPlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null,
            tint = GoldPrimary.copy(alpha = 0.5f),
            modifier = Modifier.size(36.dp)
        )
    }
}
