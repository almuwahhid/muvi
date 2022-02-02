package com.almuwahhid.themoviedb.app.detail

import android.view.LayoutInflater
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.PagerSnapHelper
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.app.detail.adapter.AvatarAdapter
import com.almuwahhid.themoviedb.databinding.ActivityMovieDetailBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.mapper.toListedAvatar
import com.almuwahhid.themoviedb.resources.util.base.BaseActivity
import com.almuwahhid.themoviedb.resources.util.ext.ToastShort
import com.almuwahhid.themoviedb.resources.util.ext.bounceEffect
import com.almuwahhid.themoviedb.resources.util.ext.toData
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding>() {

    private val viewModel by viewModel<MovieDetailViewModel>()

    private val favMovie : FavMovie? by lazy {
        intent.getStringExtra(GlobalConfig.KEY_INTENT)?.let {
            it.toData()
        }
    }

    private val adapterAvatar : AvatarAdapter? by lazy {
        AvatarAdapter()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMovieDetailBinding
        get() = ActivityMovieDetailBinding::inflate

    override fun observeViewModel() = with(viewModel) {
        favorited.observe(this@MovieDetailActivity, {
            favMovie!!.flag = it

            if (it) binding.btnFav.setImageResource(R.drawable.ic_love_fill)
            else binding.btnFav.setImageResource(R.drawable.ic_love_outline)
        })
        loading.observe(this@MovieDetailActivity, {
            if (it) binding.helper.helperLoading.visibility = View.VISIBLE
            else binding.helper.helperLoading.visibility = View.GONE
        })

        error_message.observe(this@MovieDetailActivity, {
            ToastShort(it)
            finish()
        })
        movie.observe(this@MovieDetailActivity, {
            syncData(it)
        })
    }

    private fun syncData(movie: FavMovie) = with(binding) {

        movie.belongs_to_collection?.let {
            adapterAvatar?.add(true, it.toListedAvatar())
        }?:fun() {
            adapterAvatar?.add(true, movie.toListedAvatar())
        }()

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvAvatar)
        indicator.attachToRecyclerView(rvAvatar, pagerSnapHelper)

        tvName.text = movie.original_title
        tvOverview.text = movie.overview?:"-"
        tvRate.text = "rating : ${movie.vote_average}"
        tvHomepage.text = HtmlCompat.fromHtml(movie.homepage, HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    override fun initView(binding: ActivityMovieDetailBinding): Unit = with(binding) {
        setSupportActionBar(appbar.toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("")
            it!!.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        rvAvatar.adapter = adapterAvatar

        favMovie?.let {
            viewModel.getDetailMovie(it.id)
            viewModel.isMovieFavorited(it.id)

            btnFav.setOnClickListener{ v ->
                viewModel.removeOrAdd(it, !it.flag)
                v.bounceEffect(this@MovieDetailActivity)
            }
        } ?: fun() {
            finish()
        }()
    }

}