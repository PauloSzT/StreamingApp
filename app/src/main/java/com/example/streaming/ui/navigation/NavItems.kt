package com.example.streaming.ui.navigation

import com.example.streaming.ui.utils.UiConstants.EMPTY_STRING

sealed class NavItem(var title: String, var route: String) {

    object MediaPlayerScreen : NavItem("Media Player", "player/{songId}") {
        fun routeWithArgs(songId: Int): String = "player/${songId}"
    }

    object HomeScreen : NavItem("Home", "home")

    companion object {
        fun String.title(): String {
            return when (this) {
                MediaPlayerScreen.route -> MediaPlayerScreen.title
                HomeScreen.route -> HomeScreen.title
                else -> EMPTY_STRING
            }
        }
    }
}
