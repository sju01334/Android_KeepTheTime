package com.nepplus.android_keepthetime.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.nepplus.android_keepthetime.databinding.FragmentSettingsBinding
import com.nepplus.android_keepthetime.dialogs.CustomAlertDialog
import com.nepplus.android_keepthetime.ui.main.LoginActivity
import com.nepplus.android_keepthetime.utils.ContextUtil
import com.nepplus.android_keepthetime.utils.GlobalData

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
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "로그 아웃"
            alert.binding.bodyTxt.text = "정말 로그아웃 하시겠습니까 ?"
            alert.binding.contentEdt.visibility = View.GONE
            alert.binding.positiveBtn.setOnClickListener {
//                로그인 토큰(ContextUtil 에 있는) 만 제거하고 싶을 떄
//                ContextUtil.setLoginToken(mContext, "")

                ContextUtil.clear(mContext)
                GlobalData.loginUser = null
                val myIntent = Intent(mContext, LoginActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

                alert.dialog.dismiss()

            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }
        }
    }

    override fun setValues() {
    }
}