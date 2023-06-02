package com.example.interactivebible.reading

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


open class ViewPagerAdapter(fm:FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle){
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    override fun getItemCount(): Int {
        return 4
    }


    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0 -> BookNamesFragment()
            1 -> ChapterFragment()
            2 -> VerseFragment()
            else -> BookCardFragment()
        }
    }







}



