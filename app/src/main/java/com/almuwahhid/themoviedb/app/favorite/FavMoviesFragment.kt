package com.almuwahhid.themoviedb.app.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.almuwahhid.themoviedb.app.detail.MovieDetailActivity
import com.almuwahhid.themoviedb.app.home.HomeViewModel
import com.almuwahhid.themoviedb.app.movies.adapter.MovieAdapter
import com.almuwahhid.themoviedb.app.favorite.adapter.FavMovieAdapter
import com.almuwahhid.themoviedb.app.home.GENRE_BY_TYPE
import com.almuwahhid.themoviedb.databinding.FragmentHomeBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.mapper.toFavorite
import com.almuwahhid.themoviedb.resources.databinding.HelperBinding
import com.almuwahhid.themoviedb.resources.util.base.BaseFragment
import com.almuwahhid.themoviedb.resources.util.base.BaseFragmentHelper
import com.almuwahhid.themoviedb.resources.util.ext.isInternetAvailable
import com.almuwahhid.themoviedb.resources.util.ext.toJson
import com.almuwahhid.themoviedb.resources.widget.RecyclerScroll
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavMoviesFragment : BaseFragmentHelper<FragmentHomeBinding>() {

    private val homeViewModel by sharedViewModel<HomeViewModel>()
    private val viewModel by viewModel<FavMoviesViewModel>()
    val adapter : FavMovieAdapter by lazy {
        FavMovieAdapter()
    }.also {
        it.value.setItemCallback { type, item ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).putExtra(GlobalConfig.KEY_INTENT, item.toJson()))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavMoviesFragment().apply {

            }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun observeViewModel()  = with(viewModel){
        loading.observe(viewLifecycleOwner, {
            initLoading(it)
        })
        favmonsters.observe(viewLifecycleOwner, {
            adapter.add(true, it)
            if(it.isEmpty()) binding.helper.helperNodata.visibility = View.VISIBLE
        })
    }

    private fun observeHomeViewModel() = with(homeViewModel) {
        update_genre.observe(viewLifecycleOwner, {
            onRefresh()
        })
        refresh_favorited.observe(viewLifecycleOwner, {
            onRefresh()
        })
    }

    override fun initView(binding: FragmentHomeBinding) = with(binding) {
        observeHomeViewModel()
        rv.adapter = adapter
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            onRefresh()
        }
        onRefresh()
    }

    override fun onRefresh() {
        super.onRefresh()
        homeViewModel.update_genre.value?.let {
            getMovies(it)
        } ?: fun() {
            getMovies(GENRE_BY_TYPE.ALL)
        }()
    }

    private fun getMovies(type : GENRE_BY_TYPE) {
        when(type) {
            is GENRE_BY_TYPE.ALL -> {
                viewModel.getFavoriteMovie()
            }
            is GENRE_BY_TYPE.SPECIFIC -> {
                viewModel.getFavoriteMovieByGenre(type.genreId)
            }
        }
    }

    private fun initLoading(isloading : Boolean) = with(binding){
        adapter.initLoading(isloading)
        if(isloading) helper.helperNodata.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun bindingHelper(binding: FragmentHomeBinding): HelperBinding {
        return binding.helper
    }
}