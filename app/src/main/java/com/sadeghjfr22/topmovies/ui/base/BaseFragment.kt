package com.sadeghjfr22.topmovies.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>> : Fragment(), BaseView {

    private var presenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }

    fun onBackPressed(view: View){

        Navigation.findNavController(view).popBackStack()
    }
}