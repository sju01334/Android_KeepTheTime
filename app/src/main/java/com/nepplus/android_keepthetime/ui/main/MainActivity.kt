package com.nepplus.android_keepthetime.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.MainViewPagerAdapter
import com.nepplus.android_keepthetime.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var mPagerAdapter : MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        titleTxt.text = "내가 만든 약속"

        mPagerAdapter = MainViewPagerAdapter(this)
        binding.mainViewPager.adapter = mPagerAdapter

        binding.mainViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNav.menu.getItem(position).isChecked = true
                    titleTxt.text = when(position){
                        0 -> "내가 만든 약속"
                        1 -> "초대 받은 약속"
                        else -> "설정"
                    }
                }
            })

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.myAppointments -> binding.mainViewPager.currentItem = 0
                R.id.invitedAppointments -> binding.mainViewPager.currentItem = 1
                R.id.settings -> binding.mainViewPager.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }

    }

}