package ru.inteam.touristo.common.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Any> intentFor(context: Context) = Intent(context, T::class.java)

inline fun <reified T : Activity> Context.startActivity(extras: Intent.() -> Unit = {}) {
    val intent = intentFor<T>(this)
    intent.extras()
    startActivity(intent)
}
