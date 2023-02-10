package com.faizansocialmediaproject.techchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.faizansocialmediaproject.techchat.Fragments.*
import com.faizansocialmediaproject.techchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val userHomeFragment = UserProfileFragment()
        val addPostFragment=AddPostFragment()

        setFragment(homeFragment)

        binding.bottomNavigationView .setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->setFragment(homeFragment)
                R.id.menu_search->setFragment(searchFragment)
                R.id.menu_add_post->setFragment(addPostFragment)
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