package com.example.twittercloneappmvp.util

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import java.io.Serializable

@MainThread
inline fun <reified Args : NavArgs> navArgs(savedStateHandle: SavedStateHandle) =
    NavArgsLazy(Args::class) {
        return@NavArgsLazy Bundle().apply {
            savedStateHandle.keys().forEach { key ->
                when (val value: Any? = savedStateHandle.get(key)) {
                    // map types for common argument types. Add more mappings as needed
                    null -> putParcelable(key, null) // it's null, doesn't matter which type
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Serializable -> putSerializable(key, value)
                    is Parcelable -> putParcelable(key, value)
                    is Bundle -> putBundle(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Double -> putDouble(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    else -> throw IllegalArgumentException(
                        "Argument ${value::class} not mapped into bundle type."
                    )
                }
            }
        }
    }

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
