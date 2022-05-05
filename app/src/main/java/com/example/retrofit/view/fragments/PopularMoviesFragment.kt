package com.example.retrofit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.FragmentPopularMoviesBinding
import com.example.retrofit.view.adapter.MovieAdapter
import com.example.retrofit.viewModel.MovieListViewModel
import com.example.retrofit.viewModel.ViewModelProviderFactory
import com.example.retrofit.viewModel.MovieListViewModelObserver

class PopularMoviesFragment: Fragment() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelObserver: MovieListViewModelObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAndObserveViewModel()
        setAdapter()
        setBindings()
        getMovies()



        binding.recyclerView.adapter = MovieAdapter(itemClickListener = viewModel.recyclerViewItemClickListener)
    }

    private fun setBindings() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMoviesCoroutine()
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun initAndObserveViewModel() {
        val viewModelProviderFactory = ViewModelProviderFactory(requireContext())
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[MovieListViewModel::class.java]

        viewModelObserver = MovieListViewModelObserver(
            context = requireContext(),
            viewModel = viewModel,
            viewLifecycleOwner = this,
            liveData = {
                when (it) {
                    is MovieListViewModel.State.ShowLoading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is MovieListViewModel.State.HideLoading -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is MovieListViewModel.State.Result -> {
                        (binding.recyclerView.adapter as MovieAdapter).submitList(it.list)
                    }
                }
            },
            openDetail = {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
//                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
              )
            }
        )
    }


    private fun getMovies() {
        viewModel.getMoviesCoroutine()
    }
}