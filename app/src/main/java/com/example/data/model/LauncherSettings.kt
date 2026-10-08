package com.example.data.model

data class LauncherSettings(
    val accentColorIndex: Int = 0, // 0=Calm Azure, 1=Lavender, 2=Mint, 3=Rose, etc.
    val gridColumns: Int = 4, // 4 or 5
    val iconScale: Float = 1.0f, // 0.85f - 1.15f
    val showLabels: Boolean = true,
    val showClock: Boolean = true,
    val showDate: Boolean = true,
    val showBattery: Boolean = true,
    val showNetwork: Boolean = true,
    val showPerformancePanel: Boolean = true,
    val wallpaperIndex: Int = 0, // 0 = Apple iOS 18 Aurora, 1 = PLX Cyber, 2 = Hex Matrix, 3 = OLED Pure, 4 = Win11 Bloom
    val appSortOrder: AppSortOrder = AppSortOrder.ALPHABETICAL_ASC,
    val pinnedDockPackages: List<String> = emptyList(),
    val pinnedHomePackages: List<String> = emptyList(),
    val customGamePackages: Set<String> = emptySet(),
    val hiddenPackages: Set<String> = emptySet(),
    val autoDetectGames: Boolean = true,
    val userName: String = "",
    val securityPasscode: String = "UHA-1",

    // New Experience Modes & OEM Capabilities
    val isIPhoneMode: Boolean = true,
    val isWindows11Mode: Boolean = false,
    val lowRamMode: Boolean = false,
    val enableSamsungEdgePanel: Boolean = true,
    val enableAppleDynamicIsland: Boolean = true,
    val enablePixelAtAGlance: Boolean = true,
    val enableXiaomiSuperFolder: Boolean = true,
    val enableNothingWidgets: Boolean = false,
    val oneHandedMode: Boolean = false,

    // Red Magic Gaming Capabilities
    val enableRedMagicHud: Boolean = true,
    val enableRedMagicRgbBorder: Boolean = false,
    val enableRedMagicMora: Boolean = true,
    val redMagicFanMode: Int = 2, // 0=Off, 1=Auto 14k, 2=Max 20k RPM
    val redMagicPerformanceMode: Int = 2 // 0=Balanced, 1=Rise, 2=Diablo Turbo
)
