package com.nepplus.android_keepthetime.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.ui.main.LoginActivity
import com.nepplus.android_keepthetime.ui.main.MainActivity
import com.nepplus.android_keepthetime.utils.ContextUtil
import com.nepplus.android_keepthetime.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    var isTokenOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        val token = ContextUtil.getLoginToken(mContext)
        Log.d("토큰", token)
        apiList.getRequestMyInfo(token).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    Log.d("성공", "여기왔니")
                    isTokenOk = true
                    GlobalData.loginUser = br.data.user
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    override fun setValues() {
        var myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent : Intent
            if(isTokenOk && ContextUtil.getAutoLogin(mContext)){
                Toast.makeText(mContext, "${GlobalData.loginUser!!.nick_name}님 환영합니다", Toast.LENGTH_SHORT).show()

                myIntent = Intent(mContext, MainActivity::class.java)

            }else{
                myIntent = Intent(mContext, LoginActivity::class.java)
            }
            startActivity(myIntent)
            finish()

        },2500)
    }
}