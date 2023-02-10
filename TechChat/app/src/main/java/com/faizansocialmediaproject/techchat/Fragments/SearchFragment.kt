package com.faizansocialmediaproject.techchat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.adapters.SlidingPageAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout

class SearchFragment : Fragment() {
   lateinit var  binding :FragmentSearchBinding
   lateinit var adapter : SlidingPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)
        setUpTabLayout()
        return binding.root
    }

    private fun setUpTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Following"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Follower"))
        binding.tabLayout.tabGravity=TabLayout.GRAVITY_FILL

        adapter = fragmentManager?.let { SlidingPageAdapter(it) }!!
        binding.viewpager.adapter=adapter

        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewpager.currentItem = tab!!.position
            }

        })

    }

}