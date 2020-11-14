package com.rzahr.architecture.utils

import android.util.Log
import com.rzahr.architecture.ApplicationHelper
import com.rzahr.architecture.R
import java.io.*
import java.util.*

/**
 * @author Rashad Zahr
 *
 * object used as a helper to build queries and to copy the database to the internal application directory
 */
object QuickDBUtils {

    /**
     * returns if the database exists
     * @return boolean value representing if the database exist
     */
    fun databaseExist(): Boolean {

        val dbName = "Database.db"
        return if (ApplicationHelper.baseContext().getDatabasePath(dbName).exists()) true

        else {

            if (!Arrays.asList(*ApplicationHelper.baseContext().assets.list("")).contains(dbName)) return false

            createDirectory(getDBPath(), false)

            val inputStream = ApplicationHelper.baseContext().assets.open(dbName)
            val out = FileOutputStream(File(getDBPath() + dbName))
            val buf = ByteArray(1024)
            while (inputStream.read(buf) > 0) out.write(buf)

            inputStream.close()
            out.flush()
            out.close()
            true
        }
    }

    /**
     * Create directory.
     * @param path        the path
     * @param withNoMedia the with no media
     * @return the state if the directory was created or not
     */
    private fun createDirectory(path: String, withNoMedia: Boolean): String {

        val folder = File(path)
        var success = true
        if (!folder.exists()) success = folder.mkdir()

        if (success) {

            val noMedia = File(
                "$path/" + ApplicationHelper.baseContext().resources.getString(
                    R.string.NO_MEDIA
                ))

            if (!noMedia.exists() && withNoMedia) {
                try {
                    noMedia.createNewFile()
                } catch (e: IOException) {
                    Log.d("architecture", e.toString())
                }
            }

            return "Success"
        }

        else return "Failure"
    }

    /**
     * copies the database to the internal application directory
     * @param dbExternalPath: the file path that will be copied
     * @param downloadedDbName: the file name
     * @return boolean value representing a successful copy of the database
     */
    fun copyDatabaseFromExternalDirectory(dbExternalPath: String, downloadedDbName: String): Boolean {

        val outFileName = getDBPath() + downloadedDbName
        val fileDirectory = File(File(outFileName).parent)
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdirs()) {
                return false
            }
        }
        createDirectory(
            getDBPath(), false
        )

        try {
            val inputStream = FileInputStream(dbExternalPath + downloadedDbName)
            val out = FileOutputStream(File(outFileName))
            val buf = ByteArray(1024)
            while (inputStream.read(buf) > 0) {
                out.write(buf)
            }

            inputStream.close()
            out.flush()
            out.close()
        }
        catch (e: FileNotFoundException){
            return false
        }

        return true
    }

    /**
     * gets the database path
     * @return db path
     */
    fun getDBPath(): String {

        return ApplicationHelper.baseContext().applicationInfo.dataDir + "/databases/"
    }
}