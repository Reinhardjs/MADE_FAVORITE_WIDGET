package com.example.made_final.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.made_final.R
import com.example.made_final.databinding.ActivityMainBinding
import com.example.made_final.ui.adapter.MyPagerAdapter
import com.example.made_final.ui.base.BaseActivity
import com.example.made_final.ui.fragment.FavoriteMovieListFragment
import com.example.made_final.ui.fragment.FavoriteTvShowListFragment
import com.example.made_final.viewmodel.MovieListViewModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<MovieListViewModel, ActivityMainBinding>() {

    lateinit var mContext: Context
    lateinit var toolbarTop: Toolbar
    lateinit var adapterViewPager: MyPagerAdapter
    lateinit var tabLayout: TabLayout

    override val layoutRes: Int = R.layout.activity_main

    override fun getViewModel(): Class<MovieListViewModel> {
        return MovieListViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = applicationContext

        val disposable = viewModel.getFavorites(applicationContext, "movies")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            for (item in it) {
                                // Toast.makeText(applicationContext, "MOVIE TITLE : " + item.title, Toast.LENGTH_LONG).show()
                            }
                        },
                        {
                            // Toast.makeText(applicationContext, "ERROR : " + it.message, Toast.LENGTH_LONG).show()
                        }
                )


        INSTANCE = this

        val viewPager = dataBinding.viewPager
        toolbarTop = dataBinding.toolbar
        tabLayout = dataBinding.tabLayout

        toolbarTop.title = getString(R.string.favorite_toolbar_title)
        setSupportActionBar(toolbarTop)

        adapterViewPager = MyPagerAdapter(mContext, supportFragmentManager)

        adapterViewPager.addFragment(FavoriteMovieListFragment.newInstance(0, "Page 1"))
        adapterViewPager.addFragment(FavoriteTvShowListFragment.newInstance(1, "Page 2"))

        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = adapterViewPager

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: MainActivity

        fun getInstance(): MainActivity {
            return INSTANCE
        }
    }
}
