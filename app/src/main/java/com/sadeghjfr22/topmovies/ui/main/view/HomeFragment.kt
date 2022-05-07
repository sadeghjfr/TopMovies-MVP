package com.sadeghjfr22.topmovies.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.sadeghjfr22.fooddelivery.utils.State
import com.sadeghjfr22.topmovies.data.model.Genres
import com.sadeghjfr22.topmovies.databinding.FragmentHomeBinding
import com.sadeghjfr22.topmovies.ui.base.BaseFragment
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import com.sadeghjfr22.topmovies.ui.base.BaseView
import com.sadeghjfr22.topmovies.ui.main.adapter.GenreAdapter
import com.sadeghjfr22.topmovies.ui.main.presenter.HomePresenterImpl
import com.sadeghjfr22.topmovies.ui.main.adapter.MovieAdapter
import com.sadeghjfr22.topmovies.ui.main.adapter.MoviesLoadingStateAdapter
import com.sadeghjfr22.topmovies.utils.Constants.TAG
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment<BaseView, BasePresenterImpl<BaseView>>(), HomeView {

    lateinit var binding: FragmentHomeBinding
    private var  movieAdapter = MovieAdapter()
    lateinit var genreAdapter: GenreAdapter
    lateinit var presenter: HomePresenterImpl<HomeView>

    override fun onCreateView(inflater:LayoutInflater,
                              container:ViewGroup?,
                              savedInstanceState:Bundle?):View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        presenter = HomePresenterImpl()
        presenter.attachView(this)
        genreAdapter =
            GenreAdapter(arrayListOf(),requireContext(),presenter,movieAdapter,viewLifecycleOwner)

        setupRecyclerView()
        setAdapterListener()
        getGenres()
        getAllMovies()

        binding.btnFilter.setOnClickListener {
            binding.rvGenres.isVisible = !binding.rvGenres.isVisible
        }

        binding.edtSearch.addTextChangedListener { query->

            if (query != null && !query.toString().equals("")) {

                searchMovies(query.toString())
                getGenres()
                binding.rvGenres.scrollToPosition(0)
            }

            else getAllMovies()
        }

        return binding.root
    }

    override fun setupRecyclerView() {

        val footerAdapter = MoviesLoadingStateAdapter({ movieAdapter.retry() })

        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(getContext(),2)
            setHasFixedSize(true)
            adapter = movieAdapter
            isVisible = false
            this.adapter = movieAdapter.withLoadStateFooter(footer = footerAdapter)
            (layoutManager as GridLayoutManager).spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == movieAdapter.itemCount  && footerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }

        binding.rvGenres.apply {

            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false)
            adapter = genreAdapter
        }

    }

    override fun setAdapterListener(){

        movieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.NotLoading){

                hideProgress()

                if (movieAdapter.itemCount>0){

                    binding.rvMovies.isVisible = true
                    binding.txtError.isVisible = false
                }

                else
                    showError(true, "Movie not found")

                Log.e(TAG,"SUCCESS")
            }

            else if (loadState.refresh is LoadState.Loading) {

                showProgress()
                showError(false, "")
                binding.rvMovies.isVisible = false
                Log.e(TAG,"LOADING")
            }

            else {

                hideProgress()
                binding.rvMovies.isVisible = false

                val error = when {

                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append  is LoadState.Error -> loadState.append  as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {

                    Log.e(TAG,"Error:"+it.error.localizedMessage)
                    showError(true, it.error.localizedMessage)
                }

            }
        }

    }

    override fun showError(visibility: Boolean, msg: String) {

        binding.txtError.text = msg
        binding.txtError.isVisible = visibility
    }

    override fun showProgress() {
        binding.spinKit.isVisible = true
    }

    override fun hideProgress() {
        binding.spinKit.isVisible = false
    }

    private fun getAllMovies(){

        viewLifecycleOwner.lifecycleScope.launch {

            presenter.getMovies().collectLatest { movies->

                movieAdapter.submitData(movies)
            }
        }

    }

    private fun getGenres(){

        presenter.getGenres().observe(viewLifecycleOwner, {

            it.let { resource ->

                when (resource.status) {

                    State.LOADING -> {
                        Log.e(TAG,"LOADING...:"+it.message.toString())
                    }

                    State.SUCCESS -> {
                        Log.e(TAG,"SUCCESS:"+it.message.toString())
                        resource.data.let { genres -> retrieveGenres(genres as ArrayList<Genres>) }
                    }

                    State.ERROR -> {
                        Log.e(TAG,"ERROR:"+it.message.toString())
                    }

                }
            }
        })

    }

    private fun retrieveGenres(genres: ArrayList<Genres>) {

        genreAdapter.apply {

            // Add default selected gere
            val genre = Genres(0, "All", true)
            genres.add(0, genre)
            addGenres(genres)
            notifyDataSetChanged()
        }
    }

    private fun searchMovies(query: String){

        viewLifecycleOwner.lifecycleScope.launch {

            presenter.searchMovies(query).collectLatest { movies->

                movieAdapter.submitData(movies)
            }
        }
    }

}