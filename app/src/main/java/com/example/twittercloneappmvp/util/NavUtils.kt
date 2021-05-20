package com.example.twittercloneappmvp.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSafe(
    navDirections: NavDirections,
    navOptions: NavOptions? = null,
) {
    val action = currentDestination?.getAction(navDirections.actionId)
        ?: graph.getAction(navDirections.actionId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(navDirections.actionId, navDirections.arguments, navOptions)
    }
}
