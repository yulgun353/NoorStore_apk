package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.language.AppLanguage
import kotlin.math.cos
import kotlin.math.sin

/**
 * Renders high-quality crisp country flags for Language selection:
 * - Uyghur: East Turkistan Sky-Blue Flag with White Crescent & Star
 * - Arabic: Syrian Flag (Green/White/Black tricolor with 3 red stars)
 * - English: USA Flag (Red/White stripes with Blue canton)
 */
@Composable
fun CountryFlag(
    language: AppLanguage,
    modifier: Modifier = Modifier,
    width: Dp = 26.dp,
    height: Dp = 17.dp,
    cornerRadius: Dp = 3.dp
) {
    val flagShape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .size(width, height)
            .shadow(1.dp, flagShape)
            .clip(flagShape)
            .border(0.5.dp, Color.Black.copy(alpha = 0.15f), flagShape)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            when (language) {
                AppLanguage.UYGHUR -> {
                    // Uyghur / East Turkistan Sky-Blue Flag
                    val skyBlue = Color(0xFF4A90E2)
                    drawRect(color = skyBlue, size = size)

                    // Crescent Outer
                    val crescentCenterX = w * 0.38f
                    val crescentCenterY = h * 0.50f
                    val outerRadius = h * 0.35f
                    drawCircle(
                        color = Color.White,
                        radius = outerRadius,
                        center = Offset(crescentCenterX, crescentCenterY)
                    )

                    // Crescent Inner (Cutout)
                    val innerRadius = outerRadius * 0.80f
                    val innerCenterX = crescentCenterX + outerRadius * 0.30f
                    drawCircle(
                        color = skyBlue,
                        radius = innerRadius,
                        center = Offset(innerCenterX, crescentCenterY)
                    )

                    // 5-Pointed Star
                    val starCenterX = w * 0.65f
                    val starCenterY = h * 0.50f
                    val outerStarRadius = h * 0.18f
                    val innerStarRadius = outerStarRadius * 0.40f

                    val starPath = Path()
                    var angle = -Math.PI / 2.0
                    val angleStep = Math.PI / 5.0

                    for (i in 0 until 10) {
                        val r = if (i % 2 == 0) outerStarRadius else innerStarRadius
                        val px = (starCenterX + cos(angle) * r).toFloat()
                        val py = (starCenterY + sin(angle) * r).toFloat()
                        if (i == 0) {
                            starPath.moveTo(px, py)
                        } else {
                            starPath.lineTo(px, py)
                        }
                        angle += angleStep
                    }
                    starPath.close()
                    drawPath(starPath, Color.White)
                }

                AppLanguage.ARABIC -> {
                    // Syrian Flag: Green (Top), White (Middle), Black (Bottom)
                    val green = Color(0xFF007A3D)
                    val white = Color(0xFFFFFFFF)
                    val black = Color(0xFF111111)
                    val redStar = Color(0xFFD52B1E)

                    val stripeHeight = h / 3f

                    drawRect(color = green, topLeft = Offset(0f, 0f), size = Size(w, stripeHeight))
                    drawRect(color = white, topLeft = Offset(0f, stripeHeight), size = Size(w, stripeHeight))
                    drawRect(color = black, topLeft = Offset(0f, stripeHeight * 2), size = Size(w, stripeHeight))

                    // 3 Red Stars in the middle white band
                    val starCenterY = h * 0.5f
                    val starRadius = stripeHeight * 0.35f
                    val innerRadius = starRadius * 0.42f
                    val starXs = listOf(w * 0.25f, w * 0.50f, w * 0.75f)

                    for (starX in starXs) {
                        val starPath = Path()
                        var angle = -Math.PI / 2.0
                        val angleStep = Math.PI / 5.0

                        for (i in 0 until 10) {
                            val r = if (i % 2 == 0) starRadius else innerRadius
                            val px = (starX + cos(angle) * r).toFloat()
                            val py = (starCenterY + sin(angle) * r).toFloat()
                            if (i == 0) {
                                starPath.moveTo(px, py)
                            } else {
                                starPath.lineTo(px, py)
                            }
                            angle += angleStep
                        }
                        starPath.close()
                        drawPath(starPath, redStar)
                    }
                }

                AppLanguage.ENGLISH -> {
                    // USA Flag: Red and White stripes with Blue canton
                    val red = Color(0xFFB22234)
                    val white = Color(0xFFFFFFFF)
                    val blue = Color(0xFF3C3B6E)

                    val stripeCount = 7
                    val stripeHeight = h / stripeCount

                    for (i in 0 until stripeCount) {
                        val stripeColor = if (i % 2 == 0) red else white
                        drawRect(
                            color = stripeColor,
                            topLeft = Offset(0f, i * stripeHeight),
                            size = Size(w, stripeHeight)
                        )
                    }

                    // Blue canton
                    val cantonWidth = w * 0.45f
                    val cantonHeight = stripeHeight * 4f
                    drawRect(
                        color = blue,
                        topLeft = Offset(0f, 0f),
                        size = Size(cantonWidth, cantonHeight)
                    )

                    // Star dots inside canton
                    val dotRadius = cantonHeight * 0.08f
                    val rows = 3
                    val cols = 3
                    for (r in 0 until rows) {
                        for (c in 0 until cols) {
                            val cx = cantonWidth * (c + 1) / (cols + 1f)
                            val cy = cantonHeight * (r + 1) / (rows + 1f)
                            drawCircle(
                                color = Color.White,
                                radius = dotRadius,
                                center = Offset(cx, cy)
                            )
                        }
                    }
                }
            }
        }
    }
}
