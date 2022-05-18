package ru.inteam.touristo.common.ui.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.hasPermission(permission: String): Boolean {
    return permissionStatus(permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.permissionStatus(permission: String): Int {
    return ContextCompat.checkSelfPermission(this, permission)
}

fun Fragment.hasPermission(permission: String): Boolean {
    return permissionStatus(permission) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.permissionStatus(permission: String): Int {
    return ContextCompat.checkSelfPermission(requireContext(), permission)
}
