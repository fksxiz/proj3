package com.example.proj3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: showFragment(MainFragment.newInstance())
    }

    fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout,fragment)
            if(fragment !is MainFragment){
                addToBackStack(null)
            }
            commit()
        }
    }
}