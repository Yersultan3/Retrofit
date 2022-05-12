package com.example.retrofit.view.fragments

import TopRatedFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.retrofit.databinding.FragmentHomeBinding
import com.example.retrofit.view.adapter.ViewPagerAdapter
import com.example.retrofit.viewModel.MovieListViewModel


class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieListViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val activity = (activity as AppCompatActivity)
            activity.setSupportActionBar(binding.include.toolbar)
            activity.supportActionBar!!.setHomeButtonEnabled(true)

            val tabViewpager = binding.tabViewpager
            val tabTabLayout = binding.tabTabLayout
            setupViewPager(tabViewpager)
            tabTabLayout.setupWithViewPager(tabViewpager)
        }

        private fun setupViewPager(viewpager: ViewPager) {
            val adapter = ViewPagerAdapter(childFragmentManager)
            adapter.addFragment(PopularMoviesFragment(), "Popular")
            adapter.addFragment(TopRatedFragment(), "Top rated")
            viewpager.adapter = adapter
        }
    }
