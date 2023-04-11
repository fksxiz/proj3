package com.example.proj3

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide

class ViewPagerEventPageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_event_page, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = arguments?.getSerializable(KEY_EVENT) as Event
        view.apply {
            findViewById<TextView>(R.id.TitleTextView).text = event.title
            findViewById<TextView>(R.id.DateTextView).text = event.createDate
            findViewById<TextView>(R.id.AnnotationTextView).text = event.annotation

            val htmlText = event.message.replace("\$nbsp;", " ")
            findViewById<TextView>(R.id.MessageTextView).text =
                Html.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    .replace("  ", " ")
                    .replace(" \n", "\n")
                    .replace("\n\n\n", "\n\n")
            Glide.with(context).load(event.imageUrl).into(findViewById(R.id.AvatarImageView))
        }
    }

    companion object {

        private const val KEY_EVENT = "KEY_EVENT"

        @JvmStatic
        fun newInstance(event: Event) =
            ViewPagerEventPageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_EVENT, event)
                }
            }
    }
}