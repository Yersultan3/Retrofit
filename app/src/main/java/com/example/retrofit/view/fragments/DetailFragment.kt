package com.example.retrofit.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.retrofit.viewModel.MovieDetailViewModel
import com.example.retrofit.R
import com.example.retrofit.databinding.FragmentDetailBinding
import com.example.retrofit.viewModel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var prefSettings: SharedPreferences


    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        private var movieId: Int = 0
        private const val TAG_UNPRESSED = "unpressed"
        private const val TAG_PRESSED = "pressed"
        private var sessionId: String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = args.movieId
        getSessionId()
        getMovie(movieId)
        onFavoriteClickListener()
        binding.progressBar.visibility = View.GONE
        val activity = (activity as AppCompatActivity)

//        val toolbar = binding.toolbar
//        val toolbar = (activity as AppCompatActivity).findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

//        val decorView: View = activity.window.decorView
//        val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        decorView.systemUiVisibility = option
//        activity.window.statusBarColor = Color.TRANSPARENT

        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setHomeButtonEnabled(true)

//        binding.toolbarLayout.title = "TabViewPagerScroll"
//        binding.viewPager.adapter = SimpleFragmentPagerAdapter(supportFragmentManager)
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }



    private fun getMovie(movieId: Int) {

        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MovieDetailViewModel::class.java]
        viewModel.getMovieById(movieId)
        viewModel.movie.observe(viewLifecycleOwner) {
            Picasso.get().load(IMAGE_URL + it.poster_path).into(binding.detailMoviePoster)
            imageActive()
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.releaseDate.text = it.release_date
//            binding.swipeRefresh.isRefreshing = false
        }
    }


    private fun imageActive() {
        viewModel.composeFavorite(sessionId, movieId)
        viewModel.compose.observe(viewLifecycleOwner) {
            if(it) {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on_ic)
//                binding.swipeRefresh.isRefreshing = false
            } else {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off_ic)
//                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun addFavorite(movieId: Int, sessionId: String) {
        viewModel.addFavorite(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on_ic)
                binding.detailStarBtn.tag = TAG_PRESSED
            }

        }
        viewModel.addFavorite(movieId, sessionId)
    }

    private fun deleteFavorite(movieId: Int, sessionId: String) {
        viewModel.deleteFavorites(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if(it) {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off_ic)
                binding.detailStarBtn.tag = TAG_UNPRESSED
            }
        }
        viewModel.deleteFavorites(movieId, sessionId)
    }

    private fun onFavoriteClickListener() {

        binding.detailStarBtn.setOnClickListener {

            if (binding.detailStarBtn.tag == TAG_PRESSED) {
                deleteFavorite(movieId, sessionId)
            } else {
                addFavorite(movieId, sessionId)
            }
        }
    }

    private fun getSessionId() {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {

          }
    }
}