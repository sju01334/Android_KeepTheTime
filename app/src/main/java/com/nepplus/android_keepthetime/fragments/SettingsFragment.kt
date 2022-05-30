package com.nepplus.android_keepthetime.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.nepplus.android_keepthetime.databinding.FragmentSettingsBinding
import com.nepplus.android_keepthetime.dialogs.CustomAlertDialog
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.ui.main.LoginActivity
import com.nepplus.android_keepthetime.utils.ContextUtil
import com.nepplus.android_keepthetime.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//            갤러리를 개발자가 이용 : 유저가 허락을 받아야함 => 권한 세팅
//            TedPermission 라이블리

//            권한이 OK 일떄
//            갤러리로 사진 가지러 이동(추가작업) =>
        }
//        닉네임 변경 이벤트
        binding.changeNickLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "준비시간 변경"
            alert.binding.bodyTxt.visibility = View.GONE
            alert.binding.contentEdt.hint = "외출준비에 몇분 걸리는지?"
            alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_NUMBER

            alert.binding.positiveBtn.setOnClickListener {

                val changedNick = alert.binding.contentEdt.text.toString()

                apiList.patchRequestEditUserInfo("ready_minute", changedNick)
                    .enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                val br = response.body()!!
                                GlobalData.loginUser = br.data.user
                                setUserData()
                                alert.dialog.dismiss()

                            } else {
                                val errorBodyStr = response.errorBody()!!.string()
                                val jsonObj = JSONObject(errorBodyStr)
                                val code = jsonObj.getInt("code")
                                val message = jsonObj.getString("message")
                                if (code == 400) {
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        }
                    })
            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }

        }
//        외출 준비시간 변경
        binding.readyTimeLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "닉네임 변경"
            alert.binding.bodyTxt.visibility = View.GONE
            alert.binding.contentEdt.hint = "변경할 닉네임을 입력해 주세요"
            alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_TEXT

            alert.binding.positiveBtn.setOnClickListener {

                val changedNick = alert.binding.contentEdt.text.toString()

                apiList.patchRequestEditUserInfo("nickname", changedNick)
                    .enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                val br = response.body()!!
                                GlobalData.loginUser = br.data.user
                                setUserData()
                                alert.dialog.dismiss()

                            } else {
                                val errorBodyStr = response.errorBody()!!.string()
                                val jsonObj = JSONObject(errorBodyStr)
                                val code = jsonObj.getInt("code")
                                val message = jsonObj.getString("message")
                                if (code == 400) {
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        }
                    })

            }

        }
//        비밀번호 변경
        binding.changePwLayout.setOnClickListener { }
//        출발장소 변경
        binding.myPlaceLayout.setOnClickListener { }
//        친구 목록 관리
        binding.changePwLayout.setOnClickListener { }
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
                myIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

                alert.dialog.dismiss()

            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }
        }

    }
    override fun setValues() {

        setUserData()

        when (GlobalData.loginUser!!.provider) {
            "kakao" -> {
            }
            "naver" -> {
            }
            else -> binding.socialLoginImg.visibility = View.GONE
        }

    }

    fun setUserData(){
        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImg)
            .into(binding.profileImg)

        binding.nicknameTxt.text = GlobalData.loginUser!!.nickname
        binding.readyTimeTxt.text = GlobalData.loginUser!!.readyMinute
    }
}