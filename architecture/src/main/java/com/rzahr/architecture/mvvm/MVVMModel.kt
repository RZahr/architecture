package com.rzahr.architecture.mvvm

import com.rzahr.architecture.ApplicationHelper
import com.rzahr.architecture.abstracts.Database

/**
 * @author Rashad Zahr
 *
 * new base model class helper for performing SQLITE queries
 */
@Suppress("unused")
abstract class MVVMModel {
    val mDatabase: Database? = ApplicationHelper.database()
}