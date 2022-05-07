package com.sadeghjfr22.topmovies.ui.base

interface BaseView {

    fun showError(visibility: Boolean, msg: String)

    fun showProgress()

    fun hideProgress()
}