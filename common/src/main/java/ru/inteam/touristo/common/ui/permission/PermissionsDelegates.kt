package ru.inteam.touristo.common.ui.permission

import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private class PermissionReadOnlyDelegate(
    private val onResult: (Boolean) -> Unit
) : ReadOnlyProperty<ActivityResultCaller, ActivityResultLauncher<String>> {
    override fun getValue(
        thisRef: ActivityResultCaller,
        property: KProperty<*>
    ): ActivityResultLauncher<String> {
        return thisRef.registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            onResult
        )
    }
}

private class MultiplePermissionReadOnlyDelegate(
    private val onResult: (Map<String, Boolean>) -> Unit
) : ReadOnlyProperty<ActivityResultCaller, ActivityResultLauncher<Array<String>>> {
    override fun getValue(
        thisRef: ActivityResultCaller,
        property: KProperty<*>
    ): ActivityResultLauncher<Array<String>> {
        return thisRef.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            onResult
        )
    }
}

fun ActivityResultCaller.permissionDelegate(
    onResult: (Boolean) -> Unit = {}
): ReadOnlyProperty<ActivityResultCaller, ActivityResultLauncher<String>> {
    return PermissionReadOnlyDelegate(onResult)
}

fun ActivityResultCaller.multiplePermissionDelegate(
    onResult: (Map<String, Boolean>) -> Unit = {}
): ReadOnlyProperty<ActivityResultCaller, ActivityResultLauncher<Array<String>>> {
    return MultiplePermissionReadOnlyDelegate(onResult)
}
