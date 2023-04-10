package com.example.proj3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
        findViewById<AppCompatButton>(R.id.ViewPagerButton).setOnClickListener{
            (activity as MainActivity).showFragment(ViewPagerFragment.newInstance())
        }

        findViewById<AppCompatButton>(R.id.ListViewButton).setOnClickListener{
            (activity as MainActivity).showFragment(ListViewFragment.newInstance())
        }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}