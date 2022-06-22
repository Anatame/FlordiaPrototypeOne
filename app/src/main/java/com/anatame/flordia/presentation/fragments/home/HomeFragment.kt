package com.anatame.flordia.presentation.fragments.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.anatame.flordia.databinding.FragmentHomeBinding
import com.anatame.flordia.presentation.fragments.home.adapter.HomeAdapter
import com.anatame.flordia.presentation.fragments.home.adapter.HomeRecyclerViewItem
import com.anatame.flordia.presentation.fragments.home.tabs.AnimeFragment
import com.anatame.flordia.presentation.fragments.home.tabs.MovieFragment
import com.anatame.flordia.presentation.fragments.home.tabs.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager


        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)

        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Movie"
                }
                1-> {
                    tab.text = "Anime"
                }
            }
        }.attach()

        return binding.root
    }
}