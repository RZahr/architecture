package com.rzahr.architecture.abstracts

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


@Suppress("unused", "MemberVisibilityCanBePrivate")
class Database @Inject constructor(@ApplicationContext val context: Context) {

    var myDataBase: SQLiteDatabase? = null

    fun getDatabase(): SQLiteDatabase? {

        return getDatabaseInstance(context)
    }

    fun getDatabaseInstance(context: Context): SQLiteDatabase? {

        if (myDataBase != null && myDataBase!!.isOpen) return myDataBase as SQLiteDatabase

        return try {
            myDataBase = SQLiteDatabase.openDatabase(
                getDBPath(context) + "Database.db",
                null,
                SQLiteDatabase.OPEN_READWRITE
            )

            myDataBase!!
        } catch (e: java.lang.Exception) {

            null
        }
    }

    fun getDBPath(context: Context): String {

        return context.applicationInfo.dataDir + "/databases/"
    }

    fun closeDatabase() {

        // my database variable is not always NOT NULL and this is because we are reusing this function for multiple purposes
        // first purpose: we want to initialize the database and make it available to read and write from
        // second purpose: we want to use the database to read and write to without having to open it each time we want to use it
        if (myDataBase != null && myDataBase!!.isOpen) myDataBase!!.close()
    }

    fun execSQL(query: String, onError: (e: Exception) -> Unit, beforeAction: () -> Unit = {}, afterAction: () -> Unit = {}, closedAction: () -> Unit = {}) {

        try {

            beforeAction()

            getDatabase()

            if (myDataBase!!.isOpen) {
                try {
                    myDataBase?.execSQL(query)
                } catch (e: Exception) {
                    onError(e)
                }
            }
            else closedAction()

            afterAction()
        }
        catch (e: Exception) {
            onError(e)
        }
    }

    @Throws(Exception::class)
    open fun multiSelect(query: String, onResult: (cursor: Cursor) -> Unit, onError: (e: Exception) -> Unit) {

        getDatabase()
        var cursor: Cursor? = null

        try {

            cursor = myDataBase!!.rawQuery(query, null)

            if (cursor!!.moveToFirst()) {

                do onResult(cursor)

                while (cursor.moveToNext())
            }
        }

        catch (e: Exception) {

            onError(e)
        }

        finally { cursor?.close() }
    }

    @Throws(Exception::class)
    open fun multiHashSelect(query: String, onError: (e: Exception) -> Unit): ArrayList<HashMap<String, String>> {

        getDatabase()

        var cursor: Cursor? = null
        val sqlDataResultArray = ArrayList<HashMap<String, String>>()

        try {

            cursor = myDataBase!!.rawQuery(query, null)

            if (cursor!!.moveToFirst()) {

                do {

                    val temp: LinkedHashMap<String, String> = LinkedHashMap()

                    for (i in 0 until cursor.columnCount) {

                        var value = cursor.getString(i)
                        if (value == null) value = ""
                        temp[cursor.columnNames[i].toUpperCase(Locale.ENGLISH)] = value
                    }

                    sqlDataResultArray.add(temp)
                }

                while (cursor.moveToNext())
            }
        }

        catch (e: Exception) {

            onError(e)
        }

        finally {

            cursor?.close()
        }

        return sqlDataResultArray
    }

    @Throws(Exception::class)
    open fun multiSelect(query: String, onError: (e: Exception) -> Unit): ArrayList<Array<String>> {

        getDatabase()

        var cursor: Cursor? = null
        val sqlDataResultArray = ArrayList<Array<String>>()
        var returnString = ""
        var temp: Array<String>

        try {

            cursor = myDataBase!!.rawQuery(query, null)

            if (cursor!!.moveToFirst()) {

                do {

                    for (i in 0 until cursor.columnCount) returnString += cursor.getString(i) + "#RZZZ&%#"

                    temp = returnString.split("#RZZZ&%#".toRegex()).toTypedArray()
                    sqlDataResultArray.add(temp)
                    returnString = ""

                } while (cursor.moveToNext())
            }

        }

        catch (e: Exception) {
            onError(e)
        }

        finally {

            cursor?.close()
        }

        return sqlDataResultArray
    }

    @Throws(Exception::class)
    open fun singleSelect(query: String, increment: Boolean = false, delimiter: String = "", defaultReturn: String = "", args: Array<String> = emptyArray(), onError: (e: Exception) -> Unit = {}): String {

        getDatabase()

        var returnedValue = defaultReturn
        var cursor: Cursor? = null

        try {

            cursor = myDataBase!!.rawQuery(query, args)
            if (cursor != null && cursor.moveToFirst()) {

                do {

                    if (increment) returnedValue += cursor.getString(0) + delimiter

                    else returnedValue = if (cursor.getString(0) == null) defaultReturn else cursor.getString(0)

                } while (cursor.moveToNext())
            }

        }
        catch (e: Exception) {

            onError(e)
        }

        finally {

            cursor?.close()
        }

        return returnedValue
    }

    open fun selectCount(tableName: String, whereClause: String = "", onError: (e: Exception) -> Unit): Int {

        var query = "SELECT COUNT(*) FROM $tableName"
        if (whereClause.trim().isNotEmpty()) query += " WHERE $whereClause"

        return singleSelect(query, false, "", "0", emptyArray(), onError).toInt()
    }

    open fun valueExist(tableName: String, columnName: String, columnValue: String, onError: (e: Exception) -> Unit): Boolean {

        val query = "SELECT 1 FROM $tableName WHERE $columnName LIKE $columnValue"

        return singleSelect(query, false, "", "0", emptyArray(), onError).toBoolean()
    }

    open fun delete(table: String, where: String, onError: (e: Exception) -> Unit) {

        val query = if (where.isNotEmpty()) "DELETE FROM $table WHERE $where"
        else "DELETE FROM $table"

        execSQL(query, onError)
    }
}