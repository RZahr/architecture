package com.rzahr.architecture.clean_architecture

import com.rzahr.architecture.ApplicationHelper
import com.rzahr.architecture.abstracts.Database

/**
 * @author Rashad Zahr
 *
 * new base Repository class helper for performing SQLITE queries
 */
@Suppress("unused")
abstract class Repository {
    val mDatabase: Database? = ApplicationHelper.database()
}