package com.faizansocialmediaproject.techchat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
   lateinit var  binding :FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }

}