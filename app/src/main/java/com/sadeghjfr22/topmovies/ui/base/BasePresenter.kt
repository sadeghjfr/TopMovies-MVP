package com.sadeghjfr22.topmovies.ui.base

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}