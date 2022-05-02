package ru.inteam.touristo.common.util

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri

fun Context.buildResourceUri(resId: Int): Uri = resources.buildResourceUri(resId)

fun Resources.buildResourceUri(resId: Int): Uri = Uri.Builder()
    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
    .authority(getResourcePackageName(resId))
    .appendPath(getResourceTypeName(resId))
    .appendPath(getResourceEntryName(resId))
    .build()
