package com.example.proj3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class ViewPagerFragment : Fragment() {

    private val eventsViewModel: EventViewModel by viewModels()

    private lateinit var eventAdapter: ViewPagerEventAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabsLayout: TabLayout
    private lateinit var dotsIndicator: WormDotsIndicator
    private lateinit var limitEditText: EditText
    private lateinit var offsetEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
            viewPager = findViewById(R.id.MainViewPager)
            tabsLayout = findViewById(R.id.MainTabLayout)
            dotsIndicator = findViewById(R.id.MainWormDotsIndicator)
            limitEditText = findViewById(R.id.limitEditText)
            offsetEditText = findViewById(R.id.offsetEditText)
            findViewById<AppCompatButton>(R.id.DownloadDataButton).setOnClickListener(buttonListener)
        }

        eventAdapter = ViewPagerEventAdapter(this)
        viewPager.adapter = eventAdapter

        eventsViewModel.isLoading.observe(this as LifecycleOwner) {
            viewPager.isVisible = !it
        }

        eventsViewModel.events.observe(this as LifecycleOwner) {
            eventAdapter.events = it
            viewPager.adapter = eventAdapter

            TabLayoutMediator(tabsLayout, viewPager) { tab, pos ->
                tab.text =
                    "${eventsViewModel.events.value?.get(pos)?.title?.substringBefore(' ')}..."
            }.attach()

            dotsIndicator.setViewPager2(viewPager)
        }
    }

    private val buttonListener = View.OnClickListener {
        with(eventsViewModel) {
            isLoading.postValue(true)
            limit = limitEditText.text.toString()
            offset = offsetEditText.text.toString()
            getEvents()
        }
    }

    companion object {

        private const val KEY_EVENT = "KEY_EVENT"

        @JvmStatic
        fun newInstance() =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}