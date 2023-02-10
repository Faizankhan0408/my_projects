package com.faizansocialmediaproject.techchat.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.faizansocialmediaproject.techchat.Fragments.AllUserFragmnet
import com.faizansocialmediaproject.techchat.Fragments.FollowerFragment
import com.faizansocialmediaproject.techchat.Fragments.FollowingFragment
import com.faizansocialmediaproject.techchat.R

class SlidingPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
      return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return AllUserFragmnet()
            }

            1 -> {
                return FollowingFragment()
            }
            2->{
             return FollowerFragment()
            }
            else ->
                return FollowerFragment()

        }
    }
}