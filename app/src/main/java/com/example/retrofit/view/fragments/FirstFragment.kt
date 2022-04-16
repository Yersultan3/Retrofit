package com.example.retrofit.view.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.*
import com.example.retrofit.view.adapter.MovieAdapter
import com.example.retrofit.databinding.FragmentFirstBinding
import com.example.retrofit.viewModel.MovieListViewModel


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MovieListViewModel

    companion object {
//        const val DOCUMENT_CODE = "post_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAndObserveViewModel()
        setAdapter()
        setBindings()

//        binding.swipeRefresh.setOnRefreshListener {
//            initAndObserveViewModel()
//        }
        binding.recyclerView.adapter = MovieAdapter(itemClickListener = viewModel.recyclerViewItemClickListener)
//            MovieAdapter(itemClickListener = this@FirstFragment)
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
        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        viewModel.liveData.observe(
            this, {
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
            }
        )

        viewModel.openDetail.observe(
            this, {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigate(
                        FirstFragmentDirections.actionFirstFragmentToDetailFragment(it)
                    )
                }
            }
        )
    }

}

