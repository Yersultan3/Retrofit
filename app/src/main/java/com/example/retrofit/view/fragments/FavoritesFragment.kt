package com.example.retrofit.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.FragmentFavoritesBinding
import com.example.retrofit.view.adapter.MovieAdapter
import com.example.retrofit.viewModel.ViewModelFavorites

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesViewModel: ViewModelFavorites
    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var adapter: MovieAdapter

    companion object {
        private var sessionId: String = ""
        private var PAGE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
        initAndObserveViewModel()
        setAdapter()
        onBackPressed()
    }

    private fun setAdapter() {
        binding.favRecyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
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
            val movies = it
            adapter = MovieAdapter(itemClickListener = favoritesViewModel.recyclerViewItemClickListener)
            adapter.submitList(movies)
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

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    favoritesViewModel.deleteSession(sessionId)
                    editor.clear().commit()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}