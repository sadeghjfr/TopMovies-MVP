package com.sadeghjfr22.topmovies.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sadeghjfr22.fooddelivery.utils.State
import com.sadeghjfr22.topmovies.data.local.AppDatabase.Companion.getDatabase
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.databinding.FragmentFavoritesBinding
import com.sadeghjfr22.topmovies.ui.base.BaseFragment
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import com.sadeghjfr22.topmovies.ui.base.BaseView

open class FavoritesFragment: BaseFragment<BaseView, BasePresenterImpl<BaseView>>(), FavoritesView {

    lateinit var binding: FragmentFavoritesBinding
    private var favorites = ArrayList<Movies>()
    private var toWatchList = ArrayList<Movies>()
    private var watchedList = ArrayList<Movies>()
    lateinit var presenter: FavoritesPresenterImpl<FavoritesView>
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        showProgress()
        setup()
        getAllFavorites()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                if (tab.position == 0)
                    setAdapter(getToWatchMovies())
                else
                    setAdapter(getWatchedMovies())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.btnBackFav.setOnClickListener { onBackPressed(it) }

        return binding.root
    }

    private fun getAllFavorites(){

        presenter.getAllFavorites().observe(viewLifecycleOwner, {

            it.let { resource ->

                when (resource.status) {

                    State.LOADING -> {
                        showProgress()
                        showError(false, "")
                    }

                    State.SUCCESS -> {
                        favorites.clear()
                        favorites.addAll(resource.data as ArrayList<Movies>)
                        hideProgress()
                        showError(false, "")
                        setAdapter(getToWatchMovies())
                    }

                    State.ERROR -> {
                        showError(true, it.message.toString())
                        hideProgress()
                    }

                }
            }
        })

    }

    private fun setAdapter(movies: ArrayList<Movies>){

        if (movies.size<1)
            showError(true, "There is no movie")

        else
            showError(false, "")

        favoriteAdapter = FavoriteAdapter(presenter, movies)
        binding.rvFavorites.adapter = favoriteAdapter
    }

    private fun getToWatchMovies(): ArrayList<Movies>{

        toWatchList.clear()
        for (item in favorites)
            if (!item.watched)
                toWatchList.add(item)

        return toWatchList
    }

    private fun getWatchedMovies(): ArrayList<Movies>{

        watchedList.clear()
        for (item in favorites)
            if (item.watched)
                watchedList.add(item)

        return watchedList
    }

    override fun setup() {

        presenter = FavoritesPresenterImpl(getDatabase(requireContext()).favoritesDao())
        presenter.attachView(this)
        favoriteAdapter = FavoriteAdapter(presenter, arrayListOf())

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("To Watch"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Watched"))

        binding.rvFavorites.apply {

            setHasFixedSize(true)
            layoutManager = GridLayoutManager(getContext(),1)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = favoriteAdapter
        }
    }

    override fun showError(visibility: Boolean, msg: String) {

        binding.txtErrorFav.text = msg
        binding.txtErrorFav.isVisible = visibility
    }

    override fun showProgress() {
        binding.spinKitFav.isVisible = true
    }

    override fun hideProgress() {
        binding.spinKitFav.isVisible = false
    }


}