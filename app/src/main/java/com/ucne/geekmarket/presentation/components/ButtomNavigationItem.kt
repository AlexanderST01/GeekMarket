package com.ucne.geekmarket.presentation.components

import androidx.compose.ui.graphics.vector.ImageVector

data class ButtomNavigationItem (
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val direction: String? = null
)