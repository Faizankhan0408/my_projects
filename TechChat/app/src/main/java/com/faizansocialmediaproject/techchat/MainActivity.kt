package com.faizansocialmediaproject.techchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.faizansocialmediaproject.techchat.Fragments.HomeFragment
import com.faizansocialmediaproject.techchat.Fragments.NotificationFragment
import com.faizansocialmediaproject.techchat.Fragments.SearchFragment
import com.faizansocialmediaproject.techchat.Fragments.UserProfileFragment
import com.faizansocialmediaproject.techchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val notificationFragment = NotificationFragment()
        val userHomeFragment = UserProfileFragment()

        setFragment(homeFragment)

        binding.bottomNavigationView .setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->setFragment(homeFragment)
                R.id.menu_search->setFragment(searchFragment)
                R.id.menu_notification->setFragment(notificationFragment)
                R.id.menu_profile->setFragment(userHomeFragment)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            commit()
        }
    }
}