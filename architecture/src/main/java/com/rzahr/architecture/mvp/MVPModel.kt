package com.rzahr.architecture.mvp

import com.rzahr.architecture.abstracts.Database
import javax.inject.Inject

/**
 * @author Rashad Zahr
 *
 * new base model class helper for performing SQLITE queries
 */
@Suppress("unused")
open class MVPModel @Inject constructor() {

    @Inject lateinit var mDatabase: Database
}