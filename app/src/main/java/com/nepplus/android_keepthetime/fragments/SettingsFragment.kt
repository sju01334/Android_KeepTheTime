package com.nepplus.android_keepthetime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.nepplus.android_keepthetime.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {

    lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
//        프로필 이미지 변경 이벤트
        binding.profileImg.setOnClickListener {

        }
//        닉네임 변경 이벤트
        binding.changeNickLayout.setOnClickListener {

        }
//        외출 준비시간 변경
        binding.readyTimeLayout.setOnClickListener {

        }
//        비밀번호 변경
        binding.changePwLayout.setOnClickListener {  }
//        출발장소 변경
        binding.myPlaceLayout.setOnClickListener {  }
//        친구 목록 관리
        binding.changePwLayout.setOnClickListener {  }
//        로그아웃
        binding.logoutLayout.setOnClickListener {

        }
    }

    override fun setValues() {
    }
}