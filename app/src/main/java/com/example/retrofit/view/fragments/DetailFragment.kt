package com.example.retrofit.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.retrofit.viewModel.MovieDetailViewModel
import com.example.retrofit.R
import com.example.retrofit.databinding.FragmentDetailBinding
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

        binding.progressBar.visibility = View.GONE

//        initAndObserveViewModel()

//        viewModel.getCoroutinesMovieById()
        binding.tvTitle.text = args.movie.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + args.movie.poster_path).into(binding.detailMoviePoster)

    }

    private fun imgActive() {
        viewModel.composeFavorite(sessionId, movieId)
        viewModel.compose.observe(viewLifecycleOwner) {
            if(it) {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on)
            } else {
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off)
            }
        }
    }
    private fun addFavorite(movieId: Int, sessionId: String) {
        viewModel.addFavorite(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.detailStarBtn.setImageResource(R.drawable.favorite_on)
                binding.detailStarBtn.tag = TAG_YELLOW
            }

        }
    }
    private fun deleteFavorite(movieId: Int, sessionId: String) {
        viewModel.deleteFavorites(movieId, sessionId)
        viewModel.addFavoriteState.observe(viewLifecycleOwner) {
            if (it){
                binding.detailStarBtn.setImageResource(R.drawable.favorite_off)
                binding.detailStarBtn.tag = TAG_WHITE
            }
        }
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
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {

        }
    }



//    private fun initAndObserveViewModel() {
//        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
//
//        viewModel.liveData.observe(
//            this,
//            {
//                Picasso.get().load("https://image.tmdb.org/t/p/w500" + it.poster_path).into(binding.detailMoviePoster)
//                binding.tvTitle.text = it.title
//            }
//        )
//    }
}