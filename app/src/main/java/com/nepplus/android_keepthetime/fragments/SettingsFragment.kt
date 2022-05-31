package com.nepplus.android_keepthetime.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentSettingsBinding
import com.nepplus.android_keepthetime.dialogs.CustomAlertDialog
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.ui.main.LoginActivity
import com.nepplus.android_keepthetime.ui.settings.MyFriendsActivity
import com.nepplus.android_keepthetime.ui.settings.MyPlaceListActivity
import com.nepplus.android_keepthetime.utils.ContextUtil
import com.nepplus.android_keepthetime.utils.GlobalData
import com.nepplus.android_keepthetime.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SettingsFragment : BaseFragment() {

    lateinit var binding: FragmentSettingsBinding

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

            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
//            갤러리로 사진 가지러 이동(추가작업) => Intent(4)
                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    startForResult.launch(myIntent)

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                }

            }

//            권한이 OK 일떄
            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()

        }

        val ocl = object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val type = p0!!.tag.toString()

                val alert = CustomAlertDialog(mContext, requireActivity())
                alert.myDialog()

                alert.binding.bodyTxt.visibility = View.GONE

                when (type) {
                    "nickname" -> {
                        alert.binding.titleTxt.text = "닉네임 변경"
                        alert.binding.contentEdt.hint = "변경할 닉네임을 입력해 주세요"
                        alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_TEXT
                    }
                    "ready_minute" -> {
                        alert.binding.titleTxt.text = "준비시간 변경"
                        alert.binding.contentEdt.hint = "외출준비에 몇분 걸리는지?"
                        alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_NUMBER
                    }
                }


                alert.binding.positiveBtn.setOnClickListener {

                    val changedEdt = alert.binding.contentEdt.text.toString()

                    apiList.patchRequestEditUserInfo(type, changedEdt)
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
                                    val message = jsonObj.getString("message")
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
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
        }

//        닉네임 변경 이벤트
        binding.changeNickLayout.setOnClickListener(ocl)
//        외출시간 변경 이벤트
        binding.readyTimeLayout.setOnClickListener(ocl)

//        비밀번호 변경 이벤트
        binding.changePwLayout.setOnClickListener { }
//        출발장소 변경 이벤트
        binding.myPlaceLayout.setOnClickListener {
            val myIntent = Intent(mContext, MyPlaceListActivity::class.java)
            startActivity(myIntent)
        }
//        친구 목록 관리 이벤트
        binding.myFriendsLayout.setOnClickListener {
            val myIntent = Intent(mContext, MyFriendsActivity::class.java)
            startActivity(myIntent)
        }
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

    fun setUserData() {
        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImg)
            .into(binding.profileImg)

        binding.nicknameTxt.text = GlobalData.loginUser!!.nickname
        binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
//            어떤 사진을 골랐는지? 파악해보자
//            임시 : 고른 사진을 profileImg 에 바로 적용만(서버전송 X)

//            data? => 이전 화면이 넘겨준 intent
//            data?.data => 선택한 사진이 들어있는 경로 정보(URI)
                val dataUri = it.data?.data

//            URi => 이미지 뷰의 사진(Glide)
                Glide.with(mContext).load(dataUri).into(binding.profileImg)

//            API 서버에 사지을 전송 => PUT  메쏘드 + ("/user/image")
//            파일을 같이 첨부 => Multipart 형식의 데이터 첨부 활용
//            Uri -> File 형태로 변환 -> 그 파일의 실제 경로를 얻어낼 필요가 있다
                val file = File(URIPathHelper().getPath(mContext, dataUri!!))

//            파일을 retrofit 에 첨부할 수 있는 => ReqeustBody => MultipartBody  형태로 변환
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
                val body = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

                apiList.putRequestUserImage(body).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            GlobalData.loginUser = response.body()!!.data.user

                            Glide.with(mContext).load(GlobalData.loginUser!!.profileImg)

                            Toast.makeText(mContext, "프로필 사진이 변경되었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })


            }
        }
}