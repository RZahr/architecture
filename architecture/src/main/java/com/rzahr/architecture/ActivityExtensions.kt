package com.rzahr.architecture

import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.statusBarAdapt() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val rootView = this.window.decorView
        if (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
            rootView.systemUiVisibility = 0

        else rootView.systemUiVisibility = rootView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}


fun AppCompatActivity.createLinearRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, withDecoration: Boolean = false) {

    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter

    if (withDecoration) recyclerView.addItemDecoration(
        DividerItemDecoration(recyclerView.context,
            DividerItemDecoration.VERTICAL)
    )
}

fun AppCompatActivity.setToolbar(toolbar: Toolbar, title: String, displayHomeAsUpEnabled: Boolean = false) {

    toolbar.title = title

    setSupportActionBar(toolbar)

    supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
}