package com.ucne.geekmarket.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun BottonBar() {
    NavigationBar {
        val items = listOf(
            ButtomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = false
            ),
            ButtomNavigationItem(
                title = "Chat",
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                hasNews = false,
                badgeCount = 45
            ),
            ButtomNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                hasNews = false
            ),


            )
        var selectedeItemIndex by rememberSaveable {
            mutableStateOf(0)
        }

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedeItemIndex == index,
                onClick = {
                    selectedeItemIndex = index
//                                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = "1")
                                }
                            } else {
                                Badge()

                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedeItemIndex)
                                item.selectedIcon
                            else
                                item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}