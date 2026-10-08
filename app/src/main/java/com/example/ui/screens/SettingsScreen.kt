package com.example.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppItem
import com.example.data.model.LauncherSettings
import com.example.ui.theme.ACCENT_THEMES
import com.example.ui.theme.PLXBlack
import com.example.ui.theme.PLXBorder
import com.example.ui.theme.PLXGlassBackdrop
import com.example.ui.theme.PLXSurface
import com.example.ui.theme.PLXTextMuted
import com.example.ui.theme.PLXTextPrimary
import com.example.ui.theme.PLXTextSecondary
import java.util.Locale

@Composable
fun SettingsScreen(
    settings: LauncherSettings,
    allApps: List<AppItem> = emptyList(),
    accentColor: Color,
    isDefaultLauncher: Boolean,
    onSetDefaultLauncher: () -> Unit,
    onAccentColorChange: (Int) -> Unit,
    onGridColumnsChange: (Int) -> Unit,
    onIconScaleChange: (Float) -> Unit,
    onShowLabelsChange: (Boolean) -> Unit,
    onShowClockChange: (Boolean) -> Unit,
    onShowDateChange: (Boolean) -> Unit,
    onShowBatteryChange: (Boolean) -> Unit,
    onShowNetworkChange: (Boolean) -> Unit,
    onShowTelemetryChange: (Boolean) -> Unit,
    onWallpaperChange: (Int) -> Unit,
    onAutoDetectGamesChange: (Boolean) -> Unit,
    onToggleHideApp: (String) -> Unit = {},
    onUnhideAllApps: () -> Unit = {},
    onUserNameChange: (String) -> Unit = {},
    onPasscodeChange: (String) -> Unit = {},
    onOpenDeveloperSettings: () -> Unit = {},
    onLaunchApp: (AppItem) -> Unit = {},
    onBackToHome: () -> Unit,
    onWindows11ModeChange: (Boolean) -> Unit = {},
    onLowRamModeChange: (Boolean) -> Unit = {},
    onBoostRam: () -> Unit = {},
    onToggleSamsungEdgePanel: (Boolean) -> Unit = {},
    onToggleAppleDynamicIsland: (Boolean) -> Unit = {},
    onTogglePixelAtAGlance: (Boolean) -> Unit = {},
    onToggleXiaomiSuperFolder: (Boolean) -> Unit = {},
    onToggleNothingWidgets: (Boolean) -> Unit = {},
    onIPhoneModeChange: (Boolean) -> Unit = {},
    onToggleRedMagicHud: (Boolean) -> Unit = {},
    onToggleRedMagicRgbBorder: (Boolean) -> Unit = {},
    onToggleRedMagicMora: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackToHome()
    }

    var showHiddenAppsDialog by remember { mutableStateOf(false) }
    var hiddenAppsSearch by remember { mutableStateOf("") }
    var hiddenFilterTab by remember { mutableStateOf(0) } // 0=All, 1=Hidden, 2=Visible
    var showPasscodeDialog by remember { mutableStateOf(false) }
    var enteredPasscode by remember { mutableStateOf("") }
    var passcodeError by remember { mutableStateOf(false) }
    var showNameDialog by remember { mutableStateOf(false) }
    var nameInput by remember(settings.userName) { mutableStateOf(settings.userName) }
    var showChangePasscodeDialog by remember { mutableStateOf(false) }
    var newPasscodeInput by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isAdbEnabled = remember {
        try {
            android.provider.Settings.Global.getInt(
                context.contentResolver,
                android.provider.Settings.Global.ADB_ENABLED,
                0
            ) == 1
        } catch (_: Throwable) {
            false
        }
    }
    val isDeviceRooted = remember {
        try {
            listOf(
                "/system/bin/su",
                "/system/xbin/su",
                "/sbin/su",
                "/system/sd/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/data/local/su"
            ).any { java.io.File(it).exists() }
        } catch (_: Throwable) {
            false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PLXBlack)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackToHome,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PLXGlassBackdrop)
                    .border(1.dp, PLXBorder, RoundedCornerShape(10.dp))
                    .testTag("settings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Home",
                    tint = PLXTextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "PLX132 SETTINGS",
                    color = PLXTextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "CUSTOMIZATION & SYSTEM CONFIG",
                    color = accentColor,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Default Launcher Status Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(PLXGlassBackdrop)
                        .border(
                            1.dp,
                            if (isDefaultLauncher) Color(0xFF00E676).copy(alpha = 0.4f) else accentColor.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .testTag("default_launcher_status_card")
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = if (isDefaultLauncher) Color(0xFF00E676) else accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "DEFAULT LAUNCHER",
                                    color = PLXTextPrimary,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = if (isDefaultLauncher) "ACTIVE" else "NOT SET",
                                color = if (isDefaultLauncher) Color(0xFF00E676) else accentColor,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isDefaultLauncher)
                                "PLX132 is configured as your primary Android home screen application."
                            else
                                "Tap below to open Android Home settings and set PLX132 as your default launcher.",
                            color = PLXTextSecondary,
                            fontSize = 12.sp
                        )

                        if (!isDefaultLauncher) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = onSetDefaultLauncher,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp)
                                    .testTag("set_default_launcher_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                            ) {
                                Text(
                                    text = "Set as Default Launcher",
                                    color = Color.Black,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Profile & Identity Section
            item {
                SettingsSectionHeader(
                    title = "PROFILE & IDENTITY",
                    icon = Icons.Default.Person,
                    accentColor = accentColor
                )
            }

            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(accentColor.copy(alpha = 0.2f))
                                    .border(1.dp, accentColor.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (settings.userName.isNotBlank()) {
                                    Text(
                                        text = settings.userName.trim().take(1).uppercase(),
                                        color = accentColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = accentColor,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (settings.userName.isNotBlank()) settings.userName else "Set Profile Name",
                                    color = PLXTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (settings.userName.isNotBlank()) "Displayed at top corner" else "Tap edit to show your name at top corner",
                                    color = PLXTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Button(
                            onClick = {
                                nameInput = settings.userName
                                showNameDialog = true
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2129)),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                text = if (settings.userName.isNotBlank()) "Edit" else "Set",
                                color = accentColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Experience Modes & Performance Section
            item {
                SettingsSectionHeader(
                    title = "EXPERIENCE MODES & PERFORMANCE",
                    icon = Icons.Default.Devices,
                    accentColor = accentColor
                )
            }

            // Apple iPhone iOS Mode Card
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PhoneIphone,
                                    contentDescription = null,
                                    tint = Color(0xFF007AFF),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Apple iPhone iOS Mode",
                                    color = PLXTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Enables authentic Apple iOS 18 look with Dynamic Island, large clock, battery/weather smart widgets, and frosted dock.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.isIPhoneMode,
                            onCheckedChange = { onIPhoneModeChange(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF007AFF),
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Windows 11 Desktop Mode Card
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Window,
                                    contentDescription = null,
                                    tint = Color(0xFF0078D4),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Windows 11 Desktop Mode",
                                    color = PLXTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Transforms launcher into a full Windows 11 desktop with centered taskbar, start menu, system tray & acrylic styling.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.isWindows11Mode,
                            onCheckedChange = { onWindows11ModeChange(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF0078D4),
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Low RAM Mode / Performance Optimizer Card
            item {
                SettingsCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Speed,
                                        contentDescription = null,
                                        tint = if (settings.lowRamMode) Color(0xFF10B981) else accentColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Low RAM / Lite Mode",
                                        color = PLXTextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Disables heavy shaders, clears memory caches, and optimizes icon rendering for low-spec devices.",
                                    color = PLXTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.lowRamMode,
                                onCheckedChange = { onLowRamModeChange(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF10B981),
                                    uncheckedTrackColor = Color(0xFF282E3B)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 1-Tap Boost RAM Button
                        Button(
                            onClick = onBoostRam,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .testTag("settings_boost_ram_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (settings.lowRamMode) Color(0xFF10B981) else accentColor)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "⚡ BOOST RAM & PURGE CACHE",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // Global Mobile Companies Section
            item {
                SettingsSectionHeader(
                    title = "GLOBAL MOBILE ECOSYSTEM",
                    icon = Icons.Default.PhoneAndroid,
                    accentColor = accentColor
                )
            }

            // Samsung One UI Features
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Samsung One UI: Edge Panel",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Floating slide-out edge handle with quick access tools, RAM booster & frequent apps.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableSamsungEdgePanel,
                            onCheckedChange = { onToggleSamsungEdgePanel(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor,
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Apple iOS Features
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Apple iOS: Dynamic Island",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Interactive expandable notch pill displaying live battery %, RAM status & controls.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableAppleDynamicIsland,
                            onCheckedChange = { onToggleAppleDynamicIsland(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor,
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Google Pixel Features
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Google Pixel: At a Glance",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Iconic smart header widget with live date, weather condition, battery & alarms.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enablePixelAtAGlance,
                            onCheckedChange = { onTogglePixelAtAGlance(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor,
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Xiaomi HyperOS Features
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Xiaomi HyperOS: Super Folders",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Large 2x2 interactive home screen folder tiles with 1-tap direct app opening.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableXiaomiSuperFolder,
                            onCheckedChange = { onToggleXiaomiSuperFolder(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor,
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Nothing OS Features
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Nothing OS: Dot-Matrix Clock",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Monochromatic dot-matrix clock and minimalist telemetry header.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableNothingWidgets,
                            onCheckedChange = { onToggleNothingWidgets(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accentColor,
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Nubia Red Magic: Floating In-Game HUD Card
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Nubia Red Magic: Floating Game HUD",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Retractable red cyber edge trigger badge with live 165 FPS meter, ICE turbofan & shoulder trigger test.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableRedMagicHud,
                            onCheckedChange = { onToggleRedMagicHud(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFFFF2438),
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Nubia Red Magic: RGB Chroma Border Lighting
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Nubia Red Magic: RGB Chroma Border Neon",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Pulsating breathing gamer neon lights along the screen borders.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableRedMagicRgbBorder,
                            onCheckedChange = { onToggleRedMagicRgbBorder(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFFFF2438),
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Nubia Red Magic: Mora AI Mascot Companion
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Nubia Red Magic: Mora AI Mascot",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Interactive gaming companion with dynamic voice quotes, cooling tips & turbo status.",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = settings.enableRedMagicMora,
                            onCheckedChange = { onToggleRedMagicMora(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF38BDF8),
                                uncheckedTrackColor = Color(0xFF282E3B)
                            )
                        )
                    }
                }
            }

            // Appearance Section
            item {
                SettingsSectionHeader(title = "APPEARANCE", icon = Icons.Default.Palette, accentColor = accentColor)
            }

            // Accent Color Chooser (Aesthetic 2 rows of 4 colors)
            item {
                SettingsCard {
                    Column {
                        Text(
                            text = "Aesthetic Theme & Accent Color",
                            color = PLXTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Calming, charming, and futuristic color styling for your launcher",
                            color = PLXTextMuted,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        // Row 1 (first 4 colors)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ACCENT_THEMES.take(4).forEachIndexed { index, theme ->
                                val isSelected = settings.accentColorIndex == index
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable { onAccentColorChange(index) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(theme.primary)
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) Color.White else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .testTag("accent_color_$index"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .background(Color.White, CircleShape)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = theme.name.split(" ").firstOrNull() ?: theme.name,
                                        color = if (isSelected) Color.White else PLXTextMuted,
                                        fontSize = 9.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Row 2 (next 4 colors)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ACCENT_THEMES.drop(4).forEachIndexed { relIndex, theme ->
                                val index = relIndex + 4
                                val isSelected = settings.accentColorIndex == index
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable { onAccentColorChange(index) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(theme.primary)
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) Color.White else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .testTag("accent_color_$index"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .background(Color.White, CircleShape)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = theme.name.split(" ").firstOrNull() ?: theme.name,
                                        color = if (isSelected) Color.White else PLXTextMuted,
                                        fontSize = 9.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Grid Columns (4 vs 5)
            item {
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Grid Columns",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${settings.gridColumns} columns in home & drawer",
                                color = PLXTextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(4, 5).forEach { cols ->
                                val isSel = settings.gridColumns == cols
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSel) accentColor else Color(0xFF1E2129))
                                        .clickable { onGridColumnsChange(cols) }
                                        .padding(horizontal = 14.dp, vertical = 7.dp)
                                        .testTag("grid_col_$cols")
                                ) {
                                    Text(
                                        text = "$cols Col",
                                        color = if (isSel) Color.Black else PLXTextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Icon Scale Slider
            item {
                SettingsCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Icon Size",
                                color = PLXTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = String.format(Locale.US, "%.0f%%", settings.iconScale * 100),
                                color = accentColor,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Slider(
                            value = settings.iconScale,
                            onValueChange = onIconScaleChange,
                            valueRange = 0.85f..1.15f,
                            steps = 3,
                            colors = SliderDefaults.colors(
                                thumbColor = accentColor,
                                activeTrackColor = accentColor,
                                inactiveTrackColor = Color(0x334B5569)
                            )
                        )
                    }
                }
            }

            // Show Labels Toggle
            item {
                SettingsToggleCard(
                    title = "App Labels",
                    subtitle = "Display app name below icons",
                    checked = settings.showLabels,
                    accentColor = accentColor,
                    onCheckedChange = onShowLabelsChange
                )
            }

            // Wallpaper Style Chooser
            item {
                SettingsCard {
                    Column {
                        Text(
                            text = "Wallpaper Style",
                            color = PLXTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Switch between high-tech gaming backdrops",
                            color = PLXTextMuted,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val wallpapers = listOf("iOS 18 Aurora", "PLX Cyber", "Hex Matrix", "OLED Pure")
                            wallpapers.forEachIndexed { idx, name ->
                                val isSel = settings.wallpaperIndex == idx
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSel) accentColor.copy(alpha = 0.2f) else Color(0xFF1E2129))
                                        .border(
                                            1.dp,
                                            if (isSel) accentColor else Color(0x224B5569),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { onWallpaperChange(idx) }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = name,
                                        color = if (isSel) accentColor else PLXTextSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Home Screen Section
            item {
                SettingsSectionHeader(title = "HOME SCREEN", icon = Icons.Default.Widgets, accentColor = accentColor)
            }

            item {
                SettingsToggleCard(
                    title = "Futuristic Clock Widget",
                    subtitle = "Show large digital gaming clock",
                    checked = settings.showClock,
                    accentColor = accentColor,
                    onCheckedChange = onShowClockChange
                )
            }

            item {
                SettingsToggleCard(
                    title = "Date Display",
                    subtitle = "Show current date and day",
                    checked = settings.showDate,
                    accentColor = accentColor,
                    onCheckedChange = onShowDateChange
                )
            }

            item {
                SettingsToggleCard(
                    title = "Battery Indicator",
                    subtitle = "Show real-time battery status & temperature",
                    checked = settings.showBattery,
                    accentColor = accentColor,
                    onCheckedChange = onShowBatteryChange
                )
            }

            item {
                SettingsToggleCard(
                    title = "Network Status",
                    subtitle = "Show Wi-Fi and Cellular status indicator",
                    checked = settings.showNetwork,
                    accentColor = accentColor,
                    onCheckedChange = onShowNetworkChange
                )
            }

            item {
                SettingsToggleCard(
                    title = "Hardware Telemetry Panel",
                    subtitle = "Display live RAM %, battery temp & storage HUD",
                    checked = settings.showPerformancePanel,
                    accentColor = accentColor,
                    onCheckedChange = onShowTelemetryChange
                )
            }

            // Gaming Section
            item {
                SettingsSectionHeader(title = "GAMING MODE", icon = Icons.Default.SportsEsports, accentColor = accentColor)
            }

            item {
                SettingsToggleCard(
                    title = "Automatic Game Detection",
                    subtitle = "Scan device package flags for game apps",
                    checked = settings.autoDetectGames,
                    accentColor = accentColor,
                    onCheckedChange = onAutoDetectGamesChange
                )
            }

            // Developer Options: Root ADB bootloader(flash OS) Section
            item {
                SettingsSectionHeader(
                    title = "ROOT ADB BOOTLOADER(FLASH OS)",
                    icon = Icons.Default.Terminal,
                    accentColor = accentColor
                )
            }

            item {
                SettingsCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(accentColor.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Terminal,
                                        contentDescription = null,
                                        tint = accentColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Root ADB bootloader(flash OS)",
                                        color = PLXTextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Firmware Flash & Kernel Debug Protocol",
                                        color = PLXTextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF1E2129))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "SUPPORT ONLY",
                                    color = Color(0xFFFFB300),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Calm System Notice Card
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF161922))
                                .border(1.dp, Color(0x334B5569), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Security,
                                        contentDescription = null,
                                        tint = Color(0xFFFFB300),
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "TECHNICAL CLEARANCE NOTICE",
                                        color = Color(0xFFFFB300),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Root, ADB debugging এবং OS Flash সংক্রান্ত অপশন ব্যবহারে সিস্টেমে মারাত্মক জটিলতা তৈরি হতে পারে। এই টুলটি সুরক্ষিত ও সীমাবদ্ধ রাখা হয়েছে।",
                                    color = PLXTextSecondary,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Technical Support Clearance Required • Protected Console",
                            color = PLXTextMuted,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    enteredPasscode = ""
                                    passcodeError = false
                                    showPasscodeDialog = true
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("support_authorization_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Authorize Access",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    newPasscodeInput = ""
                                    showChangePasscodeDialog = true
                                },
                                modifier = Modifier.height(42.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Set Passcode",
                                    color = accentColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Real Diagnostic Card for Root & ADB
            item {
                SettingsCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SYSTEM DEVELOPER DIAGNOSTICS",
                                color = PLXTextSecondary,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        DeveloperDiagRow(
                            label = "ADB Debugging",
                            value = if (isAdbEnabled) "Active" else "Inactive",
                            statusColor = if (isAdbEnabled) accentColor else PLXTextMuted
                        )
                        DeveloperDiagRow(
                            label = "Bootloader",
                            value = Build.BOOTLOADER.ifEmpty { "Locked" },
                            statusColor = PLXTextPrimary
                        )
                        DeveloperDiagRow(
                            label = "Flash OS Build",
                            value = Build.DISPLAY.take(24),
                            statusColor = PLXTextPrimary
                        )
                        DeveloperDiagRow(
                            label = "Root Privilege",
                            value = if (isDeviceRooted) "Superuser (Rooted)" else "Unrooted (Enforced)",
                            statusColor = if (isDeviceRooted) Color(0xFFFF3D00) else PLXTextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = onOpenDeveloperSettings,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .testTag("open_android_dev_settings_button"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2129))
                        ) {
                            Text(
                                text = "Open Android Developer Options",
                                color = PLXTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // About Section
            item {
                SettingsSectionHeader(title = "ABOUT", icon = Icons.Default.Info, accentColor = accentColor)
            }

            item {
                SettingsCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "PLX132 Launcher",
                                    color = PLXTextPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Version 1.0 • Build for Android 15",
                                    color = accentColor,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(accentColor)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "RELEASE",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Inspired by REDMAGIC gaming power, realme modern usability, and futuristic SMU26 styling. Completely offline-first with zero tracking.",
                            color = PLXTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Device: ${Build.MANUFACTURER.uppercase()} ${Build.MODEL}",
                                color = PLXTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "OS: Android ${Build.VERSION.RELEASE}",
                                color = PLXTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Dialog: Passcode Clearance for Root ADB bootloader(flash OS)
        if (showPasscodeDialog) {
            AlertDialog(
                onDismissRequest = {
                    showPasscodeDialog = false
                    passcodeError = false
                },
                containerColor = Color(0xFF14171F),
                shape = RoundedCornerShape(16.dp),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Support Clearance Required",
                                color = PLXTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Root ADB bootloader(flash OS)",
                                color = accentColor,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Root, ADB এবং OS Flash সংক্রান্ত সেটিংস পরিবর্তন করলে ফোনে মারাত্মক সমস্যা সৃষ্টি হতে পারে। অগ্রসর হতে কাস্টমার সাপোর্ট পাসকোড প্রদান করুন।",
                            color = PLXTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = enteredPasscode,
                            onValueChange = {
                                enteredPasscode = it
                                passcodeError = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("support_passcode_field"),
                            placeholder = {
                                Text(
                                    text = "Enter authorization passcode…",
                                    color = PLXTextMuted,
                                    fontSize = 13.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = if (passcodeError) Color(0xFFFF5252) else accentColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (enteredPasscode.isNotEmpty()) {
                                    IconButton(onClick = { enteredPasscode = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = PLXTextSecondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            isError = passcodeError,
                            supportingText = {
                                if (passcodeError) {
                                    Text(
                                        text = "অকার্যকর পাসকোড! সঠিক পাসকোড দিয়ে আবার চেষ্টা করুন।",
                                        color = Color(0xFFFF5252),
                                        fontSize = 11.sp
                                    )
                                } else {
                                    Text(
                                        text = "Technical Security Clearance",
                                        color = PLXTextMuted,
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF10121A),
                                unfocusedContainerColor = Color(0xFF10121A),
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = if (passcodeError) Color(0xFFFF5252) else PLXBorder,
                                focusedTextColor = PLXTextPrimary,
                                unfocusedTextColor = PLXTextPrimary
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (enteredPasscode.trim().equals(settings.securityPasscode, ignoreCase = true)) {
                                showPasscodeDialog = false
                                passcodeError = false
                                showHiddenAppsDialog = true
                            } else {
                                passcodeError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("verify_passcode_button")
                    ) {
                        Text(
                            text = "Authorize & Open",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showPasscodeDialog = false
                            passcodeError = false
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = PLXTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }

        // Dialog: Set User Profile Name
        if (showNameDialog) {
            AlertDialog(
                onDismissRequest = { showNameDialog = false },
                containerColor = Color(0xFF14171F),
                shape = RoundedCornerShape(16.dp),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Set Profile Name",
                            color = PLXTextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "হোম স্ক্রিনের একদম উপরের কর্নারে দেখানোর জন্য আপনার নাম লিখুন। এটি স্ক্রিনে প্রিমিয়াম স্টাইলে প্রদর্শিত হবে।",
                            color = PLXTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            modifier = Modifier.fillMaxWidth().testTag("profile_name_input_field"),
                            placeholder = { Text("e.g. Arif / Alex", color = PLXTextMuted, fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF10121A),
                                unfocusedContainerColor = Color(0xFF10121A),
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = PLXBorder,
                                focusedTextColor = PLXTextPrimary,
                                unfocusedTextColor = PLXTextPrimary
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onUserNameChange(nameInput.trim())
                            showNameDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Name", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showNameDialog = false }) {
                        Text("Cancel", color = PLXTextSecondary, fontSize = 12.sp)
                    }
                }
            )
        }

        // Dialog: Set / Change Passcode ("eta only set korar shomoi ashbe")
        if (showChangePasscodeDialog) {
            AlertDialog(
                onDismissRequest = { showChangePasscodeDialog = false },
                containerColor = Color(0xFF14171F),
                shape = RoundedCornerShape(16.dp),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Security Passcode Setup",
                            color = PLXTextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Current Passcode: ${settings.securityPasscode}\n\nনতুন পাসকোড সেট করুন। কনসোল আনলক করার জন্য পরবর্তীতে এই পাসকোডটি প্রয়োজন হবে:",
                            color = PLXTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedTextField(
                            value = newPasscodeInput,
                            onValueChange = { newPasscodeInput = it },
                            modifier = Modifier.fillMaxWidth().testTag("new_passcode_input_field"),
                            placeholder = { Text("Enter new passcode…", color = PLXTextMuted, fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF10121A),
                                unfocusedContainerColor = Color(0xFF10121A),
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = PLXBorder,
                                focusedTextColor = PLXTextPrimary,
                                unfocusedTextColor = PLXTextPrimary
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newPasscodeInput.isNotBlank()) {
                                onPasscodeChange(newPasscodeInput.trim())
                            }
                            showChangePasscodeDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Passcode", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showChangePasscodeDialog = false }) {
                        Text("Cancel", color = PLXTextSecondary, fontSize = 12.sp)
                    }
                }
            )
        }

        // Dialog: Root ADB bootloader(flash OS) - Application Isolation Console
        if (showHiddenAppsDialog) {
            AlertDialog(
                onDismissRequest = { showHiddenAppsDialog = false },
                containerColor = PLXSurface,
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Root ADB bootloader(flash OS)",
                                color = PLXTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Firmware App Isolation • Code UHA-1 Verified",
                            color = accentColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                    ) {
                        OutlinedTextField(
                            value = hiddenAppsSearch,
                            onValueChange = { hiddenAppsSearch = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("hidden_apps_search_field"),
                            placeholder = {
                                Text(
                                    text = "Search apps to isolate / open…",
                                    color = PLXTextMuted,
                                    fontSize = 12.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = PLXTextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            trailingIcon = {
                                if (hiddenAppsSearch.isNotEmpty()) {
                                    IconButton(onClick = { hiddenAppsSearch = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = PLXTextSecondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF151820),
                                unfocusedContainerColor = Color(0xFF151820),
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = PLXBorder,
                                focusedTextColor = PLXTextPrimary,
                                unfocusedTextColor = PLXTextPrimary
                            )
                        )

                        // Filter Tabs: All, Isolated, Active
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val tabs = listOf(
                                "ALL (${allApps.size})" to 0,
                                "ISOLATED (${settings.hiddenPackages.size})" to 1,
                                "ACTIVE (${(allApps.size - settings.hiddenPackages.size).coerceAtLeast(0)})" to 2
                            )
                            tabs.forEach { (label, tabIdx) ->
                                val isSelected = hiddenFilterTab == tabIdx
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) accentColor else Color(0xFF1E2129))
                                        .clickable { hiddenFilterTab = tabIdx }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        color = if (isSelected) Color.Black else PLXTextSecondary,
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        val filteredForHide = allApps.filter { app ->
                            val matchesTab = when (hiddenFilterTab) {
                                1 -> settings.hiddenPackages.contains(app.packageName)
                                2 -> !settings.hiddenPackages.contains(app.packageName)
                                else -> true
                            }
                            val matchesSearch = if (hiddenAppsSearch.isBlank()) true
                            else app.label.contains(hiddenAppsSearch, ignoreCase = true) ||
                                 app.packageName.contains(hiddenAppsSearch, ignoreCase = true)
                            matchesTab && matchesSearch
                        }

                        if (filteredForHide.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (hiddenFilterTab == 1 && settings.hiddenPackages.isEmpty()) "No isolated apps yet. Select any app below to isolate." else "No apps found",
                                    color = PLXTextMuted,
                                    fontSize = 12.sp
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(filteredForHide.size, key = { filteredForHide[it].packageName }) { idx ->
                                    val app = filteredForHide[idx]
                                    val isHidden = settings.hiddenPackages.contains(app.packageName)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isHidden) accentColor.copy(alpha = 0.12f) else Color(0xFF1A1D26))
                                            .clickable { onToggleHideApp(app.packageName) }
                                            .padding(horizontal = 10.dp, vertical = 8.dp)
                                            .testTag("hide_toggle_row_${app.packageName}"),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            if (app.iconBitmap != null) {
                                                Image(
                                                    bitmap = app.iconBitmap,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(28.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }
                                            Column(modifier = Modifier.weight(1f)) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        text = app.label,
                                                        color = PLXTextPrimary,
                                                        fontSize = 13.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        maxLines = 1,
                                                        modifier = Modifier.weight(1f, fill = false)
                                                    )
                                                    if (isHidden) {
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(RoundedCornerShape(4.dp))
                                                                .background(accentColor)
                                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                                        ) {
                                                            Text(
                                                                text = "ISOLATED",
                                                                color = Color.Black,
                                                                fontSize = 8.sp,
                                                                fontFamily = FontFamily.Monospace,
                                                                fontWeight = FontWeight.Black
                                                            )
                                                        }
                                                    }
                                                }
                                                Text(
                                                    text = app.packageName,
                                                    color = PLXTextMuted,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    maxLines = 1
                                                )
                                            }
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(accentColor)
                                                    .clickable {
                                                        onLaunchApp(app)
                                                        showHiddenAppsDialog = false
                                                    }
                                                    .padding(horizontal = 8.dp, vertical = 5.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "OPEN",
                                                    color = Color.Black,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Switch(
                                                checked = isHidden,
                                                onCheckedChange = { onToggleHideApp(app.packageName) },
                                                colors = SwitchDefaults.colors(
                                                    checkedThumbColor = Color.Black,
                                                    checkedTrackColor = accentColor,
                                                    uncheckedThumbColor = PLXTextSecondary,
                                                    uncheckedTrackColor = Color(0xFF282C37)
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showHiddenAppsDialog = false }) {
                        Text("DONE", color = accentColor, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    if (settings.hiddenPackages.isNotEmpty()) {
                        TextButton(onClick = { onUnhideAllApps() }) {
                            Text("RESTORE ALL", color = Color(0xFFFF5252), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun DeveloperDiagRow(
    label: String,
    value: String,
    statusColor: Color = PLXTextPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = PLXTextSecondary,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = statusColor,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SettingsSectionHeader(
    title: String,
    icon: ImageVector,
    accentColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = PLXTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(PLXGlassBackdrop)
            .border(1.dp, Color(0x224B5569), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        content()
    }
}

@Composable
private fun SettingsToggleCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    accentColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    SettingsCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = PLXTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = PLXTextMuted,
                    fontSize = 11.sp
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black,
                    checkedTrackColor = accentColor,
                    uncheckedThumbColor = PLXTextSecondary,
                    uncheckedTrackColor = Color(0xFF1E2129)
                )
            )
        }
    }
}
