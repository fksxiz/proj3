package com.example.proj3

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerEventAdapter(eventFragment: Fragment):FragmentStateAdapter(eventFragment) {
    var events: List<Event> = emptyList()
    override fun getItemCount(): Int {
        return events.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerEventPageFragment.newInstance(events[position])
    }

}