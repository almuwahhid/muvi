package com.almuwahhid.themoviedb.app.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.app.detail.MovieDetailActivity
import com.almuwahhid.themoviedb.app.favorite.FavMoviesFragment
import com.almuwahhid.themoviedb.app.home.adapter.CarouselAdapter
import com.almuwahhid.themoviedb.app.home.adapter.OnCarouselClick
import com.almuwahhid.themoviedb.app.movies.MoviesFragment
import com.almuwahhid.themoviedb.app.search.SearchMovieActivity
import com.almuwahhid.themoviedb.databinding.ActivityMainBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Genre
import com.almuwahhid.themoviedb.resources.data.mapper.toFavorite
import com.almuwahhid.themoviedb.resources.util.base.BaseActivity
import com.almuwahhid.themoviedb.resources.util.ext.ToastShort
import com.almuwahhid.themoviedb.resources.util.ext.toJson
import com.almuwahhid.themoviedb.resources.widget.EndlessLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO : BaseActivity with databinding supported
class HomeActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel<HomeViewModel>()

    private val _all = "Semua"

    val tab : Tab? by lazy {
        Tab(supportFragmentManager)
    }

    val carouseLayoutManager : EndlessLayoutManager? by lazy {
        EndlessLayoutManager(this@HomeActivity)
    }

    val adapterCarousel : CarouselAdapter? by lazy {
        CarouselAdapter()
    }.also {
        it.value.setItemCallback{type, item ->
            when(type) {
                OnCarouselClick.OnClick -> {
                    startActivity(
                        Intent(this@HomeActivity, MovieDetailActivity::class.java).putExtra(
                            GlobalConfig.KEY_INTENT, item.toJson())
                    )
                }
                OnCarouselClick.OnFavorited -> {
                    viewModel.removeOrAdd(item, !item.flag)
                }
            }
        }
    }

    override fun initView(binding: ActivityMainBinding): Unit = with(binding){
        setSupportActionBar(appbar.toolbar)
        supportActionBar.let {
            it!!.setTitle("")
        }
        viewpager.adapter = tab
        viewpager.setOffscreenPageLimit(2)
        tablayout.setupWithViewPager(viewpager)

        tablayout.getTabAt(0)!!.setText("Movies")
        tablayout.getTabAt(1)!!.setText("Favorite")

        viewModel.getPopularMovies()
        viewModel.getGenres()

        rvAvatar.layoutManager = carouseLayoutManager
        rvAvatar.adapter = adapterCarousel

        viewModel.sampleAsync1("11")
        viewModel.sampleAsync1("12")
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun observeViewModel() = with(viewModel) {
        genres.observe(this@HomeActivity, {
            syncGenresData(it)
        })

        lifecycleScope.launchWhenCreated {
            viewModel.async.collect {
                binding.sampleAsync.text = it
                ToastShort(it)
            }

//            viewModel.async.observe(this@HomeActivity) {
//                binding.sampleAsync.text = it
//                ToastShort(it)
//            }
        }

//        viewModel.async.observe(this@HomeActivity) {
//            binding.sampleAsync.text = it
//            ToastShort(it)
//        }

        populars.observe(this@HomeActivity, {
            syncPopulars(it)
        })
        favorited.observe(this@HomeActivity, {
            adapterCarousel?.updateCarousel(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search -> {
                startActivity(Intent(this@HomeActivity, SearchMovieActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun syncPopulars(populars : List<FavMovie>) = with(binding){
        if(populars.size > 0) poster.visibility = View.VISIBLE
        adapterCarousel?.addAll(populars)

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvAvatar)
        indicator.attachToRecyclerView(rvAvatar, pagerSnapHelper)

        if(populars.size > 1) (rvAvatar.layoutManager as EndlessLayoutManager).startSliding(populars.size)
    }

    private fun syncGenresData(list : List<Genre>) = with(binding) {
        groups.addView(newChip(_all, checked = true))
        for(x in list) {
            groups.addView(newChip(x.name, checked = false))
        }
        groups.setOnCheckedChangeListener{ chipGroup, id ->
            groups.findViewById<Chip>(groups.checkedChipId)?.let {
                val key = it.text.toString()
                viewModel.refreshMovieByGenre(if(key.equals(_all)) GENRE_BY_TYPE.ALL else GENRE_BY_TYPE.SPECIFIC(
                    list.filter { genre ->
                        genre.name.equals(key)
                    }.single().id
                ))
            }
        }
    }

    private fun newChip(key : String, checked : Boolean) : Chip {
        return Chip(this@HomeActivity).apply {
            setText(key)
            isCheckable = true

            isChecked = checked
        }
    }

    class Tab(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        val moviesFragment : MoviesFragment by lazy {
            MoviesFragment.newInstance()
        }

        val favMoviesFragment : FavMoviesFragment by lazy {
            FavMoviesFragment.newInstance()
        }

        override fun getItem(position: Int): Fragment {
            if(position == 0){
                return moviesFragment
            } else {
                return favMoviesFragment
            }

        }

        override fun getCount(): Int {
            return 2
        }
    }
}