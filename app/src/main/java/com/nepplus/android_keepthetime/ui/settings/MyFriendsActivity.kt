package com.nepplus.android_keepthetime.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.FriendViewPagerAdapter
import com.nepplus.android_keepthetime.databinding.ActivityMyFriendsBinding

class MyFriendsActivity : BaseActivity() {

    lateinit var binding : ActivityMyFriendsBinding


    lateinit var mFriendsPagerAdapter : FriendViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_friends)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        addBtn.setOnClickListener {
            val myIntent = Intent(mContext, AddFriendsActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {

        titleTxt.text = "친구 목록 관리"
        addBtn.visibility = View.VISIBLE

        mFriendsPagerAdapter = FriendViewPagerAdapter(this)
        binding.friendsListViewPager.adapter = mFriendsPagerAdapter

//        ViewPager2 + TabLayout의 결합 코드
//        파라미터 ( tablayout의 변수 ,  viewpager2의 변수
        TabLayoutMediator(binding.tabLayout, binding.friendsListViewPager){ tab, position ->
            when(position){
                0 -> tab.text = "내 친구 목록"
                else -> tab.text = "친구 추가 요청"
            }
        }.attach()


    }
}