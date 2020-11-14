package com.rzahr.architecture.mvp

/**
 * @author Rashad Zahr
 *
 * base view interface
 */
interface MVPViewInterface {

    fun setPresenter(presenter: MVPPresenter<*, *>)
}