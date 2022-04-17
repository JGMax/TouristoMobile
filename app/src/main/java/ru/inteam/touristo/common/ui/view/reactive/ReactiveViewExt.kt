package ru.inteam.touristo.common.ui.view.reactive

import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

fun View.clicks(): Flow<View> = callbackFlow {
    setOnClickListener(::trySend)
    awaitClose { setOnClickListener(null) }
}

fun View.longClicks(): Flow<View> = callbackFlow {
    setOnLongClickListener {
        trySend(it)
        isActive
    }
    awaitClose { setOnClickListener(null) }
}

fun EditText.input(): Flow<String> {
    return callbackFlow {
        val tw = addTextChangedListener { trySend(it?.toString()) }
        awaitClose { removeTextChangedListener(tw) }
    }
        .filterNotNull()
        .distinctUntilChanged()
        .debounce(100L)
}
