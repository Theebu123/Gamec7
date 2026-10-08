package com.example.ui.components.redmagic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Toys
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

// -------------------------------------------------------------
// 1. RED MAGIC RGB CHROMA BORDER LIGHTING (Breathing Edge Neon)
// -------------------------------------------------------------
@Composable
fun RedMagicRgbBorderLighting(
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isEnabled) return

    val transition = rememberInfiniteTransition(label = "rgb_border_pulse")
    val alphaAnim by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "chroma_alpha"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Left cyber neon strip
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()
                .width(4.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF2438).copy(alpha = alphaAnim),
                            Color(0xFF38BDF8).copy(alpha = alphaAnim),
                            Color(0xFFFF2438).copy(alpha = alphaAnim)
                        )
                    )
                )
        )
        // Right cyber neon strip
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(4.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF2438).copy(alpha = alphaAnim),
                            Color(0xFFAF52DE).copy(alpha = alphaAnim),
                            Color(0xFFFF2438).copy(alpha = alphaAnim)
                        )
                    )
                )
        )
    }
}

// -------------------------------------------------------------
// 2. RED MAGIC TACTICAL CROSSHAIR OVERLAY
// -------------------------------------------------------------
@Composable
fun RedMagicTacticalCrosshair(
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isEnabled) return

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(36.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            // Center Dot
            drawCircle(color = Color(0xFFFF2438), radius = 2.5f, center = center)
            // Outer tactical ring
            drawCircle(
                color = Color(0x99FF2438),
                radius = 14f,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f)
            )
            // 4 tactical tick marks
            drawLine(
                color = Color(0xFFFF4D61),
                start = Offset(center.x - 18f, center.y),
                end = Offset(center.x - 6f, center.y),
                strokeWidth = 2f
            )
            drawLine(
                color = Color(0xFFFF4D61),
                start = Offset(center.x + 6f, center.y),
                end = Offset(center.x + 18f, center.y),
                strokeWidth = 2f
            )
            drawLine(
                color = Color(0xFFFF4D61),
                start = Offset(center.x, center.y - 18f),
                end = Offset(center.x, center.y - 6f),
                strokeWidth = 2f
            )
            drawLine(
                color = Color(0xFFFF4D61),
                start = Offset(center.x, center.y + 6f),
                end = Offset(center.x, center.y + 18f),
                strokeWidth = 2f
            )
        }
    }
}

// -------------------------------------------------------------
// 3. RED MAGIC FLOATING TRIGGER BADGE & IN-GAME HUD OVERLAY
// -------------------------------------------------------------
@Composable
fun RedMagicFloatingHudTrigger(
    onOpenHud: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            .background(Color(0xD9FF2438))
            .border(1.dp, Color(0xFFFF7A88), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            .clickable { onOpenHud() }
            .padding(horizontal = 7.dp, vertical = 12.dp)
            .testTag("redmagic_floating_hud_trigger"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.SportsEsports,
                contentDescription = "RedMagic HUD",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "H\nU\nD",
                color = Color.Black,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                lineHeight = 10.sp
            )
        }
    }
}

@Composable
fun RedMagicInGameHudOverlay(
    isOpen: Boolean,
    telemetry: SystemTelemetry,
    accentColor: Color,
    onClose: () -> Unit,
    onBoostGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fanMode by remember { mutableIntStateOf(2) } // 0=Off, 1=Auto 14k, 2=Max 20k RPM
    var performanceMode by remember { mutableIntStateOf(2) } // 0=Balanced, 1=Rise, 2=Diablo
    var crosshairOn by remember { mutableStateOf(false) }
    var touchRate by remember { mutableStateOf("960Hz Turbo") }
    var l1Pressed by remember { mutableStateOf(false) }
    var r1Pressed by remember { mutableStateOf(false) }
    var moraQuoteIndex by remember { mutableIntStateOf(0) }

    val moraQuotes = listOf(
        "Mora: Diablo Overdrive engaged! Thermals optimal.",
        "Mora: ICE 13.0 active! Turbine spinning at 20,000 RPM.",
        "Mora: L1/R1 Capacitive triggers mapped with 0ms latency!",
        "Mora: Frame stabilizer locked at 165 FPS Ultra!"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "fan_speed")
    val fanSpin by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (fanMode == 2) 350 else 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fan_rotation"
    )

    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClose() }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(340.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                    .background(Color(0xFF14070A))
                    .border(1.5.dp, Color(0xFFFF2438), RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { /* prevent close on card click */ }
                    .padding(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Top header
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
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "REDMAGIC HUD",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "GAME SPACE TURBO • 165Hz",
                                    color = Color(0xFFFF4D61),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0x22FFFFFF))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Live FPS & CPU Telemetry
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // FPS Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x25FF2438))
                                .border(1.dp, Color(0x66FF2438), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text("FRAME RATE", color = Color(0xFF94A3B8), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text("165 FPS", color = Color(0xFFFF2438), fontSize = 18.sp, fontWeight = FontWeight.Black)
                                Text("STABLE 99.8%", color = Color(0xFF10B981), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // CPU Clock Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x2538BDF8))
                                .border(1.dp, Color(0x6638BDF8), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text("CPU CLOCK", color = Color(0xFF94A3B8), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text("3.36 GHz", color = Color(0xFF38BDF8), fontSize = 18.sp, fontWeight = FontWeight.Black)
                                Text("Adreno 719MHz", color = Color(0xFFE2E8F0), fontSize = 9.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ICE 13.0 Turbofan Active Cooling Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0x18FFFFFF))
                            .border(1.dp, Color(0x25FFFFFF), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0x33FF2438)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Toys,
                                        contentDescription = null,
                                        tint = if (fanMode > 0) Color(0xFFFF2438) else Color.Gray,
                                        modifier = Modifier
                                            .size(26.dp)
                                            .rotate(if (fanMode > 0) fanSpin else 0f)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("ICE 13.0 TURBOFAN", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = when (fanMode) {
                                            2 -> "20,000 RPM (MAX COOL)"
                                            1 -> "14,000 RPM (AUTO)"
                                            else -> "OFF"
                                        },
                                        color = if (fanMode > 0) Color(0xFF38BDF8) else Color.Gray,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text("Thermals: 31°C (-12°C drop)", color = Color(0xFF10B981), fontSize = 9.sp)
                                }
                            }

                            // Fan mode toggle button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x33FF2438))
                                    .border(1.dp, Color(0xFFFF2438), RoundedCornerShape(8.dp))
                                    .clickable { fanMode = (fanMode + 1) % 3 }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text("CHANGE", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Red Magic Capacitive Shoulder Triggers (L1 & R1) Interactive Test
                    Text(
                        text = "SHOULDER TRIGGERS (520Hz DUAL TOUCH)",
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
                        // L1 Trigger
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (l1Pressed) Color(0xFFFF2438) else Color(0x22FFFFFF))
                                .border(1.dp, if (l1Pressed) Color(0xFFFF7A88) else Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                                .clickable {
                                    l1Pressed = !l1Pressed
                                }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "L1 TRIGGER",
                                    color = if (l1Pressed) Color.Black else Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = if (l1Pressed) "FIRED! 0ms" else "Tap to Test",
                                    color = if (l1Pressed) Color.Black else Color(0xFF94A3B8),
                                    fontSize = 9.sp
                                )
                            }
                        }

                        // R1 Trigger
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (r1Pressed) Color(0xFFFF2438) else Color(0x22FFFFFF))
                                .border(1.dp, if (r1Pressed) Color(0xFFFF7A88) else Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                                .clickable {
                                    r1Pressed = !r1Pressed
                                }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "R1 TRIGGER",
                                    color = if (r1Pressed) Color.Black else Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = if (r1Pressed) "FIRED! 0ms" else "Tap to Test",
                                    color = if (r1Pressed) Color.Black else Color(0xFF94A3B8),
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Red Magic Mora AI Mascot Companion Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0x2238BDF8))
                            .border(1.dp, Color(0x5538BDF8), RoundedCornerShape(14.dp))
                            .clickable { moraQuoteIndex = (moraQuoteIndex + 1) % moraQuotes.size }
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF38BDF8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = "Mora Mascot",
                                    tint = Color.Black,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "REDMAGIC MORA (AI MASCOT)",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = moraQuotes[moraQuoteIndex],
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 1-Tap Turbo Diablo Memory Cleanse
                    Button(
                        onClick = onBoostGame,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("redmagic_hud_purge_button"),
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
                                text = "PURGE CACHE & DIABLO BOOST",
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
