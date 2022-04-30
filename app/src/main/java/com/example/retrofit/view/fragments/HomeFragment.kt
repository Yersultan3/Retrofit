package com.example.retrofit.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.FragmentHomeBinding
import com.example.retrofit.view.adapter.MovieAdapter
import com.example.retrofit.viewModel.MovieListViewModel
import com.example.retrofit.viewModel.ViewModelProviderFactory
import com.example.retrofitexample.viewmodel.MovieListViewModelObserver

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelObserver:MovieListViewModelObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
//        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
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
                        FirstFragmentDirections.actionFirstFragmentToDetailFragment(it)
//                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
              )
            }
        )
    }


//        viewModel.liveData.observe(
//            this, {
//                when (it) {
//                    is MovieListViewModel.State.ShowLoading -> {
//                        binding.swipeRefresh.isRefreshing = true
//                    }
//                    is MovieListViewModel.State.HideLoading -> {
//                        binding.swipeRefresh.isRefreshing = false
//                    }
//                    is MovieListViewModel.State.Result -> {
//                        (binding.recyclerView.adapter as MovieAdapter).submitList(it.list)
//                    }
//                }
//            }
//        )

//        viewModel.openDetail.observe(
//            this, {
//                it.getContentIfNotHandled()?.let {
//                    findNavController().navigate(
//                        FirstFragmentDirections.actionFirstFragmentToDetailFragment(it)
////                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
//                    )
//                }
//            }
//        )
//    }

    private fun getMovies() {
        viewModel.getMoviesCoroutine()
    }
}