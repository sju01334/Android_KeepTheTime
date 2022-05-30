package com.nepplus.android_keepthetime.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.ui.main.LoginActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        apiList.getRequestMyInfo()
    }

    override fun setValues() {
        var myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent : Intent
            myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finish()

        },2500)
    }
}