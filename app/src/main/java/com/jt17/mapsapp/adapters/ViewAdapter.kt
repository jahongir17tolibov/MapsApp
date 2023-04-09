package com.jt17.mapsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jt17.mapsapp.fragments.MapsFragment
import com.jt17.mapsapp.fragments.YandexMaps

class ViewAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUMS_TAB = 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MapsFragment()
            1 -> return YandexMaps()
        }
        return MapsFragment()
    }

    override fun getItemCount(): Int = NUMS_TAB
}
