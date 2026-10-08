package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.PLXBlack
import com.example.ui.theme.PLXDeepGraphite

@Composable
fun PLXBackground(
    wallpaperIndex: Int,
    accentColor: Color,
    lowRamMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (lowRamMode) {
        // Pure lightweight OLED dark canvas without bitmap decoding
        Box(modifier = modifier.fillMaxSize().background(PLXBlack))
        return
    }

    Box(modifier = modifier.fillMaxSize().background(PLXBlack)) {
        when (wallpaperIndex) {
            0 -> {
                // Apple iOS 18 Silk Aurora / Atmospheric Fluid Bloom
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Deep cosmic dark slate base
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF04060E),
                                Color(0xFF0B1124),
                                Color(0xFF070B18)
                            )
                        )
                    )

                    // iOS Silk Bloom Ribbon 1: Royal Azure Glow (Top Right to Center)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x770A84FF),
                                Color(0x330A84FF),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.8f, size.height * 0.22f),
                            radius = size.width * 0.85f
                        ),
                        radius = size.width * 0.85f,
                        center = Offset(size.width * 0.8f, size.height * 0.22f)
                    )

                    // iOS Silk Bloom Ribbon 2: Deep Violet / Indigo Ribbon (Left Mid to Center)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x665E5CE6),
                                Color(0x28BF5AF2),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.15f, size.height * 0.48f),
                            radius = size.width * 0.9f
                        ),
                        radius = size.width * 0.9f,
                        center = Offset(size.width * 0.15f, size.height * 0.48f)
                    )

                    // iOS Silk Bloom Ribbon 3: Warm Sunset Magenta / Coral Accent (Bottom Right)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x44FF375F),
                                Color(0x18FF9F0A),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.75f, size.height * 0.78f),
                            radius = size.width * 0.75f
                        ),
                        radius = size.width * 0.75f,
                        center = Offset(size.width * 0.75f, size.height * 0.78f)
                    )

                    // Ambient Frosted Glass Shimmer
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.25f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            )
                        )
                    )
                }
            }
            1 -> {
                // High-resolution generated PLX Cyber wallpaper with dark scrim
                AsyncImage(
                    model = R.drawable.plx_wallpaper,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    PLXBlack.copy(alpha = 0.55f),
                                    Color.Black.copy(alpha = 0.45f),
                                    PLXBlack.copy(alpha = 0.82f)
                                )
                            )
                        )
                )
            }
            2 -> {
                // Procedural Cyber Carbon Matrix
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                PLXDeepGraphite,
                                PLXBlack
                            ),
                            center = Offset(size.width * 0.5f, size.height * 0.35f),
                            radius = size.width * 1.2f
                        )
                    )

                    val step = 60f
                    val lineColor = Color(0x12FFFFFF)
                    var x = 0f
                    while (x < size.width) {
                        drawLine(
                            color = lineColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = 1f
                        )
                        x += step
                    }

                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1f
                        )
                        y += step
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.12f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.85f, size.height * 0.15f),
                            radius = size.width * 0.5f
                        ),
                        radius = size.width * 0.5f,
                        center = Offset(size.width * 0.85f, size.height * 0.15f)
                    )
                }
            }
            else -> {
                // Pure OLED Minimalist Black with subtle bottom sheen
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF060709),
                                    Color(0xFF0B0C0E),
                                    Color(0xFF040506)
                                )
                            )
                        )
                )
            }
        }
    }
}
