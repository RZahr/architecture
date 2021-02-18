package com.rzahr.architecture

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

fun AppCompatActivity.statusBarAdapt() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val rootView = this.window.decorView
        if (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
            rootView.systemUiVisibility = 0

        else rootView.systemUiVisibility = rootView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

/**
 * @return the file URI
 */
fun File.getFileURI(context: Context): Uri {

    return if (Build.VERSION.SDK_INT >= 24) FileProvider.getUriForFile(context, context.packageName + ".provider", this) else Uri.fromFile(this)
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


/**
 * opens google map application if available
 * @param uri: the uri of the application
 */
fun AppCompatActivity.openGoogleMapsApp(uri: String) {

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")

    if (packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0)  this.startActivity(intent)

    else Toast.makeText(this.applicationContext, "Please Install Google Navigation", Toast.LENGTH_LONG).show()
}