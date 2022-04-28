package ru.inteam.touristo.common.ui.toolbar

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.attachToolbar(
    toolbar: Toolbar,
    onNavigationClick: View.OnClickListener = View.OnClickListener { onBackPressed() }
) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener(onNavigationClick)
}
