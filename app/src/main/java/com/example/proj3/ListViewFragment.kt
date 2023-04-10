package com.example.proj3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.viewModels


class ListViewFragment : Fragment() {
private val eventViewModel: EventViewModel by viewModels()

private lateinit var eventAdapter:EventAdapter
private lateinit var eventListView:ListView
private lateinit var limitEditText:EditText
private lateinit var offsetEditText:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            eventAdapter = EventAdapter(requireContext())
            view.apply {
                eventListView = findViewById(R.id.listView)
                limitEditText = findViewById(R.id.limitEditText)
                offsetEditText = findViewById(R.id.offsetEditText)
                findViewById<Button>(R.id.button).setOnClickListener(buttonListener)
            }

        eventListView.adapter=eventAdapter
        eventAdapter.notifyDataSetChanged()

        eventViewModel.isLoading.observe(viewLifecycleOwner){
            eventListView.isEnabled=it
        }


        eventViewModel.events.observe(viewLifecycleOwner){
            eventAdapter.events = eventViewModel.events.value.orEmpty()
            eventAdapter.notifyDataSetChanged()
        }
    }

    private val buttonListener = View.OnClickListener {
        with(eventViewModel){
            isLoading.postValue(true)
            limit =limitEditText.text.toString()
            offset =offsetEditText.text.toString()
            getEvents()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ListViewFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}