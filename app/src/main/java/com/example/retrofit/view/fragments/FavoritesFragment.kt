package com.example.retrofit.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.retrofit.databinding.FragmentFavoritesBinding
import com.example.retrofit.view.adapter.MovieAdapter
import com.example.retrofit.viewModel.ViewModelFavorites
import java.lang.RuntimeException



class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesViewModel: ViewModelFavorites
    private lateinit var prefSettings: SharedPreferences
    private lateinit var adapter: MovieAdapter

    companion object {
        private var sessionId: String = ""
        private var PAGE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserveViewModel()
        getSessionId()
    }

    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }


    private fun initAndObserveViewModel() {
        favoritesViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[ViewModelFavorites::class.java]
        favoritesViewModel.getFavorites(sessionId, PAGE)
        binding.swipeRefresh.isRefreshing = true
        favoritesViewModel.movies.observe(
            viewLifecycleOwner
        ) {
            val movie=it
//            adapter = MovieAdapter(list = movie, viewModel.recyclerViewItemClickListener)
            adapter = MovieAdapter(itemClickListener = favoritesViewModel.recyclerViewItemClickListener)
            adapter.submitList(it)
            binding.favRecyclerView.adapter = adapter
            binding.swipeRefresh.isRefreshing = false
        }

        favoritesViewModel.openDetail.observe(
            viewLifecycleOwner
        ) {
//            it.getContentIfNotHandled()?.let { movie ->
//                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment22(movie.id)
//                findNavController().navigate(action)
//            }

        }
    }


}