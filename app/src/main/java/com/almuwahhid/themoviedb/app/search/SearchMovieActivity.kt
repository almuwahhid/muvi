package com.almuwahhid.themoviedb.app.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.app.detail.MovieDetailActivity
import com.almuwahhid.themoviedb.app.movies.adapter.MovieAdapter
import com.almuwahhid.themoviedb.databinding.ActivityMovieSearchBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.mapper.toFavorite
import com.almuwahhid.themoviedb.resources.databinding.HelperBinding
import com.almuwahhid.themoviedb.resources.util.base.BaseActivityHelper
import com.almuwahhid.themoviedb.resources.util.ext.toJson
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieActivity : BaseActivityHelper<ActivityMovieSearchBinding>() {

    private val viewModel by viewModel<SearchMovieViewModel>()

    val adapter : MovieAdapter by lazy {
        MovieAdapter()
    }.also {
        it.value.setItemCallback { type, item ->
            startActivity(
                Intent(this@SearchMovieActivity, MovieDetailActivity::class.java).putExtra(GlobalConfig.KEY_INTENT, item.toFavorite().toJson())
            )
        }
    }

    override fun initView(binding: ActivityMovieSearchBinding) = with(binding) {
        setSupportActionBar(appbar.toolbar)
        appbar.toolbarTitle.text = "Pencarian Movie"
        overridePendingTransition(R.anim.pull_in_top, R.anim.pull_stay)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("")
            it!!.setHomeAsUpIndicator(R.drawable.ic_ep_circle_close_white)
        }

        edtSearch.initChangedListener{
            if(!it.equals("")) viewModel.debounceSearch(it)
        }

        rv.adapter = adapter
    }

    override fun onPause() {
        overridePendingTransition(R.anim.pull_stay, R.anim.push_out_bottom)
        super.onPause()
    }

    override fun onRefresh() = with(binding) {
        super.onRefresh()
        viewModel.searchMovies(edtSearch.text.toString())
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMovieSearchBinding
        get() = ActivityMovieSearchBinding::inflate

    override fun observeViewModel() = with(viewModel) {
        loading.observe(this@SearchMovieActivity, {
            adapter.initLoading(it)
        })
        keysearch.observe(this@SearchMovieActivity, {
            onRefresh()
        })
        movies.observe(this@SearchMovieActivity, {
            adapter.addAll(it)

            if(it.size == 0) {
                binding.helper.servererror.visibility = View.VISIBLE
                binding.helper.servererror.text = "Movie tidak tersedia"
            }
        })

        error_message.observe(this@SearchMovieActivity, {
            adapter.addAll(arrayListOf())
            binding.helper.servererror.visibility = View.VISIBLE
            binding.helper.servererror.text = it
            binding.helper.btnRefresh.visibility = View.VISIBLE
        })
    }

    override fun bindingHelper(binding: ActivityMovieSearchBinding): HelperBinding {
        return binding.helper
    }

}