package com.example.ui.components.redmagic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Propane
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Toys
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SystemTelemetry
import com.example.ui.theme.PLXCrimsonRed

@Composable
fun RedMagicGameSpaceDashboard(
    telemetry: SystemTelemetry,
    accentColor: Color,
    onBoostGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    // RedMagic Performance Mode: 0=Balanced, 1=Rise Mode, 2=Diablo Turbo
    var performanceMode by remember { mutableIntStateOf(2) }
    // Fan speed: 0=Off, 1=Auto, 2=Max 20000 RPM
    var fanMode by remember { mutableIntStateOf(2) }
    var touchSamplingRate by remember { mutableStateOf("960Hz Turbo") }
    var shoulderTriggersEnabled by remember { mutableStateOf(true) }
    var crosshairAimAssist by remember { mutableStateOf(true) }
    var rgbLightingMode by remember { mutableIntStateOf(0) } // 0=Diablo Red, 1=Cyber Cyan, 2=Rainbow

    val infiniteTransition = rememberInfiniteTransition(label = "fan_rotation")
    val fanRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (fanMode == 2) 400 else 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fan_spin"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E080C),
                        Color(0xFF0F0F16)
                    )
                )
            )
            .border(
                1.dp,
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF2438),
                        accentColor,
                        Color(0xFFFF2438)
                    )
                ),
                RoundedCornerShape(22.dp)
            )
            .padding(16.dp)
            .testTag("redmagic_game_space_dashboard")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header: RedMagic Brand & Mora Mascot status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFF2438)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = "RedMagic",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "REDMAGIC",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "GAME SPACE",
                                color = Color(0xFFFF2438),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Text(
                            text = "Mora Mascot: Active • Thermals 31°C",
                            color = Color(0xFF94A3B8),
                            fontSize = 10.sp
                        )
                    }
                }

                // 165Hz Ultra Display Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x33FF2438))
                        .border(1.dp, Color(0xFFFF2438), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "165Hz ULTRA",
                        color = Color(0xFFFF4D61),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Diablo Mode / Rise Mode Selector
            Text(
                text = "PERFORMANCE ENGINE PROFILE",
                color = Color(0xFF64748B),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RedMagicEngineTab(
                    title = "BALANCED",
                    subtitle = "60-90 FPS",
                    isSelected = performanceMode == 0,
                    selectedColor = Color(0xFF38BDF8),
                    modifier = Modifier.weight(1f)
                ) { performanceMode = 0 }

                RedMagicEngineTab(
                    title = "RISE MODE",
                    subtitle = "120 FPS",
                    isSelected = performanceMode == 1,
                    selectedColor = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                ) { performanceMode = 1 }

                RedMagicEngineTab(
                    title = "DIABLO TURBO",
                    subtitle = "165 FPS MAX",
                    isSelected = performanceMode == 2,
                    selectedColor = Color(0xFFFF2438),
                    modifier = Modifier.weight(1f)
                ) { performanceMode = 2 }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ICE Cooling Turbofan Simulation & Telemetry Gauges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Turbofan Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x22FFFFFF))
                        .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(14.dp))
                        .clickable { fanMode = (fanMode + 1) % 3 }
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Spinning Fan Icon
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0x22FF2438)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Toys,
                                contentDescription = "Cooling Fan",
                                tint = if (fanMode > 0) Color(0xFFFF2438) else Color.Gray,
                                modifier = Modifier
                                    .size(24.dp)
                                    .rotate(if (fanMode > 0) fanRotation else 0f)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "ICE TURBOFAN",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = when (fanMode) {
                                    2 -> "20,000 RPM (MAX)"
                                    1 -> "AUTO (14,000 RPM)"
                                    else -> "FAN OFF"
                                },
                                color = if (fanMode > 0) Color(0xFF38BDF8) else Color.Gray,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Touch & Trigger Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x22FFFFFF))
                        .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(14.dp))
                        .clickable { shoulderTriggersEnabled = !shoulderTriggersEnabled }
                        .padding(10.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SHOULDER TRIGGERS",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (shoulderTriggersEnabled) Color(0xFF10B981) else Color.Gray)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (shoulderTriggersEnabled) "L1/R1 960Hz Active" else "Triggers Mapped Off",
                            color = Color(0xFF94A3B8),
                            fontSize = 9.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Crosshair Aim Assist & RGB Light Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Aim Assist Toggle
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (crosshairAimAssist) Color(0x33FF2438) else Color(0x22FFFFFF))
                        .border(1.dp, if (crosshairAimAssist) Color(0xFFFF2438) else Color(0x22FFFFFF), RoundedCornerShape(10.dp))
                        .clickable { crosshairAimAssist = !crosshairAimAssist }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CenterFocusStrong,
                            contentDescription = null,
                            tint = if (crosshairAimAssist) Color(0xFFFF2438) else Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (crosshairAimAssist) "AIM ASSIST ON" else "AIM ASSIST OFF",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // RGB Neon Strip Toggle
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x22FFFFFF))
                        .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(10.dp))
                        .clickable { rgbLightingMode = (rgbLightingMode + 1) % 3 }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LightMode,
                            contentDescription = null,
                            tint = when (rgbLightingMode) {
                                0 -> Color(0xFFFF2438)
                                1 -> Color(0xFF38BDF8)
                                else -> Color(0xFFAF52DE)
                            },
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (rgbLightingMode) {
                                0 -> "RGB: DIABLO RED"
                                1 -> "RGB: CYBER CYAN"
                                else -> "RGB: RAINBOW"
                            },
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1-Tap Turbo Boost Button
            Button(
                onClick = onBoostGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .testTag("redmagic_turbo_boost_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2438))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Whatshot,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ENGAGE REDMAGIC TURBO OVERCLOCK",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun RedMagicEngineTab(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) selectedColor.copy(alpha = 0.2f) else Color(0x18FFFFFF))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) selectedColor else Color(0x22FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color(0xFF94A3B8),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = subtitle,
            color = if (isSelected) selectedColor else Color(0xFF64748B),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
