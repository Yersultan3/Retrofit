package com.example.retrofit.view.fragments

import PopularMoviesFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.retrofit.databinding.FragmentFirstBinding
import com.example.retrofit.view.adapter.ViewPagerAdapter
import com.example.retrofit.viewModel.MovieListViewModel
import com.google.android.material.tabs.TabLayout


class FirstFragment: Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MovieListViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentFirstBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val tabViewpager = binding.tabViewpager
            val tabTablayout = binding.tabTablayout
            setupViewPager(tabViewpager)
            tabTablayout.setupWithViewPager(tabViewpager)
//            getMovies()
        }

        private fun setupViewPager(viewpager: ViewPager) {
//            var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
            val adapter = ViewPagerAdapter(requireFragmentManager())


            adapter.addFragment(HomeFragment(), "Home")
            adapter.addFragment(PopularMoviesFragment(), "Popular")
            viewpager.adapter = adapter
        }
//        fun getMovies() {
//        viewModel.getMoviesCoroutine()
//        }
}



//    private lateinit var binding: FragmentFirstBinding
//    private lateinit var viewModel: MovieListViewModel
//
//    companion object {
////        const val DOCUMENT_CODE = "post_id"
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentFirstBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initAndObserveViewModel()
//        setAdapter()
//        setBindings()
//
////        binding.swipeRefresh.setOnRefreshListener {
////            initAndObserveViewModel()
////        }
//        binding.recyclerView.adapter = MovieAdapter(itemClickListener = viewModel.recyclerViewItemClickListener)
////            MovieAdapter(itemClickListener = this@FirstFragment)
//    }
//
//    private fun setBindings() {
//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.getMoviesCoroutine()
//        }
//    }
//
//    private fun setAdapter() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(
//            context,
//            RecyclerView.VERTICAL,
//            false
//        )
//    }
//
//    private fun initAndObserveViewModel() {
//        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
//
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
//
//        viewModel.openDetail.observe(
//            this, {
//                it.getContentIfNotHandled()?.let {
//                    findNavController().navigate(
//                        FirstFragmentDirections.actionFirstFragmentToDetailFragment(it)
//                    )
//                }
//            }
//        )
//    }

//}

