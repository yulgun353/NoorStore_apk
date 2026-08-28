package com.example.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.GoldPrimary
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

object ImageStorageHelper {
    fun saveUriToAppStorage(context: Context, uri: Uri): String? {
        return try {
            val imagesDir = File(context.filesDir, "product_images").apply {
                if (!exists()) mkdirs()
            }
            val fileName = "prod_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg"
            val destFile = File(imagesDir, fileName)

            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(destFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
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

    val isFile = remember(trimmed) {
        trimmed.startsWith("/") || trimmed.startsWith("file://")
    }

    val isWebOrContent = remember(trimmed) {
        trimmed.startsWith("http://") || trimmed.startsWith("https://") || trimmed.startsWith("content://")
    }

    val drawableResId = remember(trimmed) {
        if (isFile || isWebOrContent || trimmed.isBlank()) {
            0
        } else {
            val cleanName = trimmed.substringBeforeLast(".")
            context.resources.getIdentifier(cleanName, "drawable", context.packageName)
        }
    }

    when {
        drawableResId != 0 -> {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
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
        isWebOrContent -> {
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
        trimmed.isNotBlank() -> {
            // Might be a coil-loadable string or asset
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
