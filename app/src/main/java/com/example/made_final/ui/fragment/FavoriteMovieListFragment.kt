package com.example.made_final.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.made_final.R
import com.example.made_final.data.Resource
import com.example.made_final.data.Status
import com.example.made_final.data.entity.BaseEntity
import com.example.made_final.data.entity.FavoriteEntity
import com.example.made_final.databinding.FragmentListBinding
import com.example.made_final.ui.activity.MovieDetailsActivity
import com.example.made_final.ui.adapter.FavoriteAdapter
import com.example.made_final.ui.base.BaseFragment
import com.example.made_final.ui.callback.ListCallback
import com.example.made_final.viewmodel.MovieListViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteMovieListFragment(var page: Int, var title: String) :
    BaseFragment<MovieListViewModel, FragmentListBinding>(),
        ListCallback<FavoriteEntity> {

    lateinit var mContext: Context

    lateinit var resource: Resource<List<FavoriteEntity>>
    lateinit var adapter: FavoriteAdapter

    constructor() : this(1, "")

    public override val layoutRes: Int
        get() = R.layout.fragment_list

    public override fun getViewModel(): Class<MovieListViewModel> {
        return MovieListViewModel::class.java
    }

    override fun onItemClicked(imageView: ImageView, movieEntity: FavoriteEntity) {
        if (activity != null) {

            val intent = Intent(mContext, MovieDetailsActivity::class.java)
            val transitionName = ViewCompat.getTransitionName(imageView)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,
                imageView as View,
                transitionName!!
            )

            intent.putExtra("URL", movieEntity.getBackdropImageUrl())
            intent.putExtra("TRANSITION_NAME", transitionName)
            intent.putExtra("MOVIE_DATA", movieEntity as BaseEntity)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("MYAPP", "ON SAVE INSTANCE STATE on Fragment")
        outState.putSerializable("resource", resource)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        mContext = activity!!.applicationContext
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        adapter = FavoriteAdapter(this)
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        dataBinding.recyclerView.adapter = adapter

        return dataBinding.root
    }

    override fun onResume() {
        super.onResume()

        val disposable: Disposable = viewModel.getFavorites(activity!!.applicationContext,"movies")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {

                    if (it != null) {
                        dataBinding.loginProgress.visibility = View.GONE
                    }

                    val resource = Resource(null, it, null)
                    resource.status = Status.LOADING

                    dataBinding.resource = resource
                    this.resource = resource

//                    Toast.makeText(
//                        context,
//                        getString(R.string.data_load_success),
//                        Toast.LENGTH_SHORT
//                    ).show()
                },
                {
                    it.printStackTrace()
                    Log.d("MYAPP", "Exception for get favorite items : " + it.message)
                }
            )
    }

    companion object {
        @JvmStatic
        fun newInstance(page: Int, title: String): FavoriteMovieListFragment {
            val fragment = FavoriteMovieListFragment(page, title)
            return fragment
        }
    }

}