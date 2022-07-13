package com.anatame.flordia.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anatame.flordia.databinding.FragmentHomeBinding
import com.anatame.flordia.presentation.fragments.home.tabs.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


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