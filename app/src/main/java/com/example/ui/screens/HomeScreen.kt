package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppItem
import com.example.data.model.LauncherSettings
import com.example.data.model.SystemTelemetry
import com.example.ui.components.AppIconItem
import com.example.ui.components.LowRamOptimizationBar
import com.example.ui.components.PLXTopStatusBar
import com.example.ui.components.ios.IosBatteryWidget
import com.example.ui.components.ios.IosControlCenterSheet
import com.example.ui.components.ios.IosFrostedDock
import com.example.ui.components.ios.IosSpotlightSearchPill
import com.example.ui.components.ios.IosWeatherWidget
import com.example.ui.components.oem.AppleDynamicIsland
import com.example.ui.components.oem.PixelAtAGlanceWidget
import com.example.ui.components.oem.SamsungEdgePanel
import com.example.ui.components.oem.XiaomiSuperFolderWidget
import com.example.ui.components.redmagic.RedMagicFloatingHudTrigger
import com.example.ui.components.redmagic.RedMagicInGameHudOverlay
import com.example.ui.components.redmagic.RedMagicRgbBorderLighting
import com.example.ui.theme.PLXTextMuted
import com.example.ui.theme.Win11Blue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    pinnedApps: List<AppItem>,
    dockApps: List<AppItem>,
    allApps: List<AppItem> = emptyList(),
    telemetry: SystemTelemetry,
    settings: LauncherSettings,
    accentColor: Color,
    isDefaultLauncher: Boolean,
    onAppClick: (AppItem) -> Unit,
    onAppLongClick: (AppItem) -> Unit,
    onOpenDrawer: () -> Unit,
    onOpenGamingHub: () -> Unit,
    onOpenSettings: () -> Unit,
    onSetDefaultLauncher: () -> Unit,
    onBoostRam: () -> Unit,
    onToggleWin11Mode: () -> Unit,
    onToggleIPhoneMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showControlCenter by remember { mutableStateOf(false) }
    var showRedMagicHud by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragEnd = {},
                    onVerticalDrag = { change, dragAmount ->
                        if (dragAmount < -35) {
                            change.consume()
                            onOpenDrawer()
                        } else if (dragAmount > 35) {
                            change.consume()
                            showControlCenter = true
                        }
                    }
                )
            }
    ) {
        // RedMagic Chroma RGB Pulsating Edge Neon
        RedMagicRgbBorderLighting(
            isEnabled = settings.enableRedMagicRgbBorder || showRedMagicHud
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Apple Dynamic Island (Top Capsule)
            if (settings.enableAppleDynamicIsland) {
                AppleDynamicIsland(
                    telemetry = telemetry,
                    accentColor = accentColor,
                    onBoostRam = onBoostRam
                )
            }

            // Top Status Bar (With custom username displayed at the top corner with charming calm design)
            PLXTopStatusBar(
                telemetry = telemetry,
                accentColor = accentColor,
                userName = settings.userName,
                onLogoClick = onOpenSettings,
                showBattery = settings.showBattery,
                showNetwork = settings.showNetwork
            )

            // Mode Switching & Quick Feature Nav Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // iPhone iOS Mode Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (settings.isIPhoneMode) Color(0x35007AFF) else Color(0x22FFFFFF))
                            .border(1.dp, if (settings.isIPhoneMode) Color(0xFF007AFF) else Color(0x22FFFFFF), RoundedCornerShape(10.dp))
                            .clickable { onToggleIPhoneMode() }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("home_mode_iphone_pill")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneIphone,
                            contentDescription = "iPhone Mode",
                            tint = if (settings.isIPhoneMode) Color(0xFF007AFF) else Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "iPHONE",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // RedMagic Gaming HUD Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x33FF2438))
                            .border(1.dp, Color(0x66FF2438), RoundedCornerShape(10.dp))
                            .clickable { showRedMagicHud = true }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("home_quick_gaming_pill")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = "RedMagic Gaming HUD",
                            tint = Color(0xFFFF4D61),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "REDMAGIC",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // iOS Control Center Quick Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x28FFFFFF))
                            .border(1.dp, Color(0x25FFFFFF), RoundedCornerShape(10.dp))
                            .clickable { showControlCenter = true }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("home_quick_control_center_pill")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "iOS Control Center",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "CONTROL",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // Windows 11 Desktop Mode Toggle Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x330078D4))
                            .border(1.dp, Win11Blue.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .clickable { onToggleWin11Mode() }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("home_quick_win11_pill")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Window,
                            contentDescription = "Windows 11 Mode",
                            tint = Win11Blue,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "WIN 11",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Settings Icon Button
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x22FFFFFF))
                        .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(10.dp))
                        .testTag("home_settings_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Launcher Settings",
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            // Apple iOS Large Clock Glance (when in iPhone mode and showClock is enabled)
            if (settings.isIPhoneMode && settings.showClock && !settings.lowRamMode) {
                val now = remember { Date() }
                val timeFormat = remember { SimpleDateFormat("h:mm", Locale.getDefault()) }
                val dateFormat = remember { SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dateFormat.format(now).uppercase(Locale.getDefault()),
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = timeFormat.format(now),
                        color = Color.White,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = (-1).sp
                    )
                }
            }

            // Apple iOS Smart Widgets Row (Battery Rings & Weather)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IosBatteryWidget(
                    telemetry = telemetry,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f)
                )

                IosWeatherWidget(
                    modifier = Modifier.weight(1f)
                )
            }

            // RedMagic Diablo Quick Trigger Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x25FF2438))
                    .border(1.dp, Color(0x44FF2438), RoundedCornerShape(12.dp))
                    .clickable { showRedMagicHud = true }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("home_redmagic_turbo_banner")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = null,
                            tint = Color(0xFFFF2438),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "REDMAGIC DIABLO HUD • 165Hz & 960Hz",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "OPEN HUD >",
                        color = Color(0xFFFF4D61),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Low RAM Mode Bar (if lowRamMode active)
            if (settings.lowRamMode) {
                LowRamOptimizationBar(
                    telemetry = telemetry,
                    accentColor = accentColor,
                    lowRamModeActive = true,
                    onBoostRam = onBoostRam
                )
            }

            // Google Pixel At a Glance (if enabled and not in low ram mode)
            if (settings.enablePixelAtAGlance && !settings.lowRamMode) {
                PixelAtAGlanceWidget(
                    telemetry = telemetry,
                    accentColor = accentColor
                )
            }

            // Xiaomi HyperOS: Super Folder Preview (if enabled)
            if (settings.enableXiaomiSuperFolder && pinnedApps.size >= 4) {
                XiaomiSuperFolderWidget(
                    title = "Super Folder",
                    folderApps = pinnedApps.take(4),
                    accentColor = accentColor,
                    onLaunchApp = onAppClick,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Main Grid of Pinned / Favorite Apps (iOS Squircles)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (pinnedApps.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "SWIPE UP FOR ALL APPS",
                            color = accentColor,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Long-press any app in the drawer to pin it to home",
                            color = PLXTextMuted,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    val displayApps = if (settings.enableXiaomiSuperFolder && pinnedApps.size >= 4) {
                        pinnedApps.drop(4)
                    } else {
                        pinnedApps
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(settings.gridColumns),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        contentPadding = PaddingValues(vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(displayApps, key = { it.packageName }) { app ->
                            AppIconItem(
                                app = app,
                                iconScale = settings.iconScale,
                                showLabel = settings.showLabels,
                                onClick = { onAppClick(app) },
                                onLongClick = { onAppLongClick(app) }
                            )
                        }
                    }
                }
            }

            // iOS Spotlight "Search" Pill
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IosSpotlightSearchPill(onClick = onOpenDrawer)
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Apple iOS Frosted Bottom Dock
            IosFrostedDock(
                dockApps = dockApps,
                onAppClick = onAppClick,
                onAppLongClick = onAppLongClick,
                onOpenDrawer = onOpenDrawer
            )
        }

        // RedMagic Floating HUD Trigger Badge on Right Edge
        if (settings.enableRedMagicHud) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 0.dp)
            ) {
                RedMagicFloatingHudTrigger(
                    onOpenHud = { showRedMagicHud = true }
                )
            }
        }

        // Samsung One UI: Edge Panel (Right slide-out)
        if (settings.enableSamsungEdgePanel) {
            SamsungEdgePanel(
                apps = if (allApps.isNotEmpty()) allApps else pinnedApps,
                accentColor = accentColor,
                onLaunchApp = onAppClick,
                onBoostRam = onBoostRam,
                onOpenSettings = onOpenSettings
            )
        }

        // Apple iOS: Control Center Overlay Sheet
        IosControlCenterSheet(
            isOpen = showControlCenter,
            telemetry = telemetry,
            accentColor = accentColor,
            onClose = { showControlCenter = false },
            onBoostRam = onBoostRam,
            onOpenSettings = onOpenSettings
        )

        // RedMagic In-Game Gaming HUD Overlay Sheet
        RedMagicInGameHudOverlay(
            isOpen = showRedMagicHud,
            telemetry = telemetry,
            accentColor = accentColor,
            onClose = { showRedMagicHud = false },
            onBoostGame = {
                onBoostRam()
                showRedMagicHud = false
            }
        )
    }
}
