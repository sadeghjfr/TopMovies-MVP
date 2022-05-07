package com.sadeghjfr22.topmovies.ui.base

open class BasePresenterImpl<V: BaseView>: BasePresenter<V> {

    private var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }


}