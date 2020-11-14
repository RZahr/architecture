package com.rzahr.architecture

import java.util.*

/**
 * saves integer in shared preference
 * @param id: the id pointing to this integer value
 */
fun Int.addWithId(id: String) {

    ApplicationHelper.pref().setIntValue(id, this)
}

/**
 * saves string in shared preference
 * @param id: the id pointing to this string value
 */
fun String.addWithId(id: String) {

    ApplicationHelper.pref().setString(id, this)
}

/**
 * saves double in shared preference
 * @param id: the id pointing to this double value
 */
fun Double.addWithId(id: String) {

    ApplicationHelper.pref().setString(id, this.toString())
}

/**
 * saves long in shared preference
 * @param id: the id pointing to this long value
 */
fun Long.addWithId(id: String) {

    ApplicationHelper.pref().setLong(id, this)
}

/**
 * saves boolean in shared preference
 * @param id: the id pointing to this boolean value
 */
fun Boolean.addWithId(id: String) {

    ApplicationHelper.pref().setBoolean(id, this)
}

/**
 * saves string in shared preference as default
 * @param id: the id pointing to this string value
 */
fun String.addAsDefaultWithId(id: String) {

    ApplicationHelper.pref().setStringDefault(id, this)
}

/**
 * saves long in shared preference as default
 * @param id: the id pointing to this string value
 */
fun Long.addAsDefaultWithId(id: String) {

    ApplicationHelper.pref().setLongDefault(id, this)
}

/**
 * get shared Long value.
 * @return the Long
 */
fun String.getLongPrefValue(): Long {
    return ApplicationHelper.pref().getLong(this)
}

/**
 * get shared boolean value boolean.
 * @return the boolean
 */
fun String.getBoolPrefValue(): Boolean {
    return ApplicationHelper.pref().getBoolean(this)
}

/**
 * get shared String value String.
 * @return the String
 */
fun String.getStringPrefValue(): String {
    return ApplicationHelper.pref().get(this)
}

/**
 * get shared Int value Int.
 * @return the Int
 */
fun String.getIntPrefValue(): Int {
    return ApplicationHelper.pref().getInt(this)
}

/**
 * @return the preference value of the key passed
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified I>String.rzPrefVal(): I {

    return when (I::class.java.simpleName.toLowerCase(Locale.ENGLISH)) {

        "string" -> ApplicationHelper.pref().get(this) as I
        "int" -> ApplicationHelper.pref().getInt(this) as I
        "integer" -> ApplicationHelper.pref().getInt(this) as I
        "boolean" -> ApplicationHelper.pref().getBoolean(this) as I
        "bool" -> ApplicationHelper.pref().getBoolean(this) as I
        "long" -> ApplicationHelper.pref().getLong(this) as I
        else -> ApplicationHelper.pref().get(this) as I
    }
}