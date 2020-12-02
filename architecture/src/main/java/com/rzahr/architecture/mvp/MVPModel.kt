package com.rzahr.architecture.mvp

import com.rzahr.architecture.ApplicationHelper
import com.rzahr.architecture.abstracts.Database

/**
 * @author Rashad Zahr
 *
 * new base model class helper for performing SQLITE queries
 */
@Suppress("unused")
abstract class MVPModel {

    val mDatabase: Database? = ApplicationHelper.database()
}