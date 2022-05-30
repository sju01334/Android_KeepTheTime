package com.nepplus.android_keepthetime.adapters

import InvitedAppointmentsFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nepplus.android_keepthetime.fragments.MyAppointmentsFragment
import com.nepplus.android_keepthetime.fragments.SettingsFragment

class MainViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MyAppointmentsFragment()
            1 -> InvitedAppointmentsFragment()
            else -> SettingsFragment()
        }
    }


}