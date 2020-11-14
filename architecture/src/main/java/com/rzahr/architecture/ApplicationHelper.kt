package com.rzahr.architecture

import android.app.Activity
import android.app.Application
import android.app.IntentService
import android.app.Service
import android.content.Context
import android.os.Bundle
import com.rzahr.architecture.abstracts.Database
import com.rzahr.architecture.utils.QuickDBUtils
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * @author Rashad Zahr
 *
 * this class is *required* to be created from the application class
 */
@Suppress("unused")
class ApplicationHelper @Inject constructor(var quickPref: QuickPref, val database: Database, val application: Application): Application.ActivityLifecycleCallbacks {

    init {
        instance = this
        application.registerActivityLifecycleCallbacks(this)
        QuickDBUtils.databaseExist()
    }

    var currentActivity: WeakReference<Activity?>? = null

    override fun onActivityPaused(activity: Activity?) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity?) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity?) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        currentActivity = WeakReference(activity)
    }

    companion object {

        private var instance: ApplicationHelper? = null

        fun pref() = instance!!.quickPref

        fun database() = instance!!.database

        fun currentActivity() = instance?.currentActivity?.get()

        fun baseContext(): Context = instance?.application!!.baseContext

        fun applicationContext() = instance!!.application

        fun get(activity: Activity): Application = activity.application

        fun get(service: IntentService): Application = service.application

        fun get(service: Service): Application =  service.application
    }
}