package com.example.retrofit.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.retrofit.viewModel.MovieDetailViewModel
import com.example.retrofit.R
import com.example.retrofit.databinding.FragmentDetailBinding
import com.example.retrofit.viewModel.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var prefSettings: SharedPreferences


    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        private const val TAG_WHITE = "white"
        private const val TAG_YELLOW = "yellow"
        private var movieId: Int = 0
        private var sessionId: String = ""
        private var PAGE = 1
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

    }

    private fun getMovie(movieId: Int) {
        binding.swipeRefresh.isRefreshing=true
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MovieDetailViewModel::class.java]
        viewModel.getMovieById(movieId)
        viewModel.movie.observe(viewLifecycleOwner) {
            Picasso.get().load(IMAGE_URL + it.poster_path).into(binding.detailMoviePoster)
            imgActive()
            binding.tvTitle.text = it.title
            binding.swipeRefresh.isRefreshing = false
        }
    }


    private fun imgActive() {
        viewModel.composeFavorite(sessionId, movieId)
        viewModel.compose.observe(viewLifecycleOwner) {
            if(it) {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on_ic)
                binding.swipeRefresh.isRefreshing = false
            } else {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off_ic)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun addFavorite(movieId: Int, sessionId: String) {
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on_ic)
                binding.detailStarBtn.tag = TAG_YELLOW
            }

        }
        viewModel.addFavorite(movieId, sessionId)
    }

    private fun deleteFavorite(movieId: Int, sessionId: String) {
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off_ic)
                binding.detailStarBtn.tag = TAG_WHITE
            }
        }
        viewModel.deleteFavorites(movieId, sessionId)
    }

    private fun onFavoriteClickListener() {

        binding.detailStarBtn.setOnClickListener {

            if (binding.detailStarBtn.tag == TAG_YELLOW) {
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