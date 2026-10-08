package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// PLX132 Base Dark Cybernetic & Calming Palette
val PLXBlack = Color(0xFF0B0F17)
val PLXDeepGraphite = Color(0xFF111726)
val PLXSurface = Color(0xFF171F30)
val PLXSurfaceVariant = Color(0xFF1E283D)
val PLXGlassBackdrop = Color(0xEB131B2A)
val PLXBorder = Color(0xFF28354A)
val PLXBorderSubtle = Color(0x224B5569)

// Calm & Aesthetic Accent Palette (Soft, charming, non-aggressive)
val PLXCalmAzure = Color(0xFF38BDF8)         // Calming Sky / Ocean Azure (Default)
val PLXCalmAzureGlow = Color(0x3338BDF8)

val PLXLavenderCharm = Color(0xFFA78BFA)     // Dreamy Pastel Lavender
val PLXLavenderCharmGlow = Color(0x33A78BFA)

val PLXZenMint = Color(0xFF34D399)           // Soothing Zen Sage / Mint
val PLXZenMintGlow = Color(0x3334D399)

val PLXRoseQuartz = Color(0xFFFB7185)        // Soft Aesthetic Rose
val PLXRoseQuartzGlow = Color(0x33FB7185)

val PLXCyberCyan = Color(0xFF00E5FF)         // Crisp Arctic Cyan
val PLXCyberCyanGlow = Color(0x3300E5FF)

val PLXChampagneAmber = Color(0xFFFBBF24)    // Warm Champagne Gold
val PLXChampagneAmberGlow = Color(0x33FBBF24)

val PLXStealthSilver = Color(0xFFCBD5E1)     // Minimalist Silver Slate
val PLXStealthSilverGlow = Color(0x33CBD5E1)

val PLXCrimsonRed = Color(0xFFFF4D61)        // Softened Neon Crimson (Optional)
val PLXCrimsonGlow = Color(0x33FF4D61)

// Windows 11 Fluent Theme Colors
val Win11TaskbarBg = Color(0xEE181E2A)
val Win11FlyoutBg = Color(0xF5202636)
val Win11Blue = Color(0xFF0078D4)
val Win11Hover = Color(0x1FFFFFFF)
val Win11Border = Color(0x338899B5)

// System & Text
val PLXTextPrimary = Color(0xFFF8FAFC)
val PLXTextSecondary = Color(0xFF94A3B8)
val PLXTextMuted = Color(0xFF64748B)

val PLXSuccess = Color(0xFF10B981)
val PLXWarning = Color(0xFFF59E0B)
val PLXError = Color(0xFFEF4444)
val PLXCardHighlight = Color(0x12FFFFFF)

data class AccentTheme(
    val name: String,
    val primary: Color,
    val glow: Color
)

val ACCENT_THEMES = listOf(
    AccentTheme("Calm Azure", PLXCalmAzure, PLXCalmAzureGlow),
    AccentTheme("Lavender Charm", PLXLavenderCharm, PLXLavenderCharmGlow),
    AccentTheme("Zen Mint", PLXZenMint, PLXZenMintGlow),
    AccentTheme("Rose Quartz", PLXRoseQuartz, PLXRoseQuartzGlow),
    AccentTheme("Cyber Cyan", PLXCyberCyan, PLXCyberCyanGlow),
    AccentTheme("Champagne Gold", PLXChampagneAmber, PLXChampagneAmberGlow),
    AccentTheme("Stealth Slate", PLXStealthSilver, PLXStealthSilverGlow),
    AccentTheme("Crimson Cyber", PLXCrimsonRed, PLXCrimsonGlow)
)
