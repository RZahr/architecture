package com.rzahr.architecture

import android.content.Context
import androidx.preference.PreferenceManager
/**
 * @author Rashad Zahr
 *
 * class used for shared preference
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class QuickPref(val context: Context) {

    /**
     * set shared boolean value.
     * @param id    the id
     * @param value the value
     */
    fun setBoolean(id: String, value: Boolean?) {

        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putBoolean(id, value!!)
        editor.apply()
    }

    /**
     * set shared long value.
     * @param id    the id
     * @param value the value
     */
    fun setLong(id: String, value: Long?) {

        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putLong(id, value!!)
        editor.apply()
    }

    /**
     * set shared string value.
     * @param id    the id
     * @param value the value
     */
    fun setString(id: String, value: String) {

        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(id, value)
        editor.apply()
    }

    /**
     * check shared pref value if exist boolean.
     * @param value the value
     * @return the boolean
     */
    fun checkSharedPrefValueIfExist(value: String): Boolean {

        return PreferenceManager.getDefaultSharedPreferences(context).contains(value)
    }

    /**
     * set shared string default value.
     * @param id    the id
     * @param value the value
     */
    fun setStringDefault(id: String, value: String) {

        if (!checkSharedPrefValueIfExist(id)) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(id, value)
            editor.apply()
        }
    }

    /**
     * set shared long default value.
     * @param id    the id
     * @param value the value
     */
    fun setLongDefault(id: String, value: Long?) {

        if (!checkSharedPrefValueIfExist(id)) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(id, value!!)
            editor.apply()
        }
    }

    fun setIntValue(id: String, value: Int) {

        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putInt(id, value)
        editor.apply()
    }

    /**
     * get shared string value string.
     * @param id the id
     * @return the string
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun get(id: String): String {

        return if (PreferenceManager.getDefaultSharedPreferences(context).getString(id, "") == null) "" else PreferenceManager.getDefaultSharedPreferences(context).getString(id, "")!!
    }

    /**
     * get shared string value string.
     * @param id the id
     * @return the string
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getInt(id: String): Int {

        return PreferenceManager.getDefaultSharedPreferences(context).getInt(id, -1)
    }

    /**
     * get shared boolean value boolean.
     * @param id the id
     * @return the boolean
     */
    fun getBoolean(id: String): Boolean {

        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(id, false)
    }

    /**
     * get shared long value long.
     * @param id the id
     * @return the long
     */
    fun getLong(id: String): Long {

        return  PreferenceManager.getDefaultSharedPreferences(context).getLong(id, 0)
    }
}