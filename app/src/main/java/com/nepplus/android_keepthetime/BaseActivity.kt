package com.nepplus.android_keepthetime

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nepplus.android_keepthetime.api.APIList
import com.nepplus.android_keepthetime.api.ServerApi
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext : Context
    lateinit var retrofit: Retrofit
    lateinit var apiList : APIList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        retrofit = ServerApi.getRetrofit()
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun setupEvents()
    abstract  fun setValues()
}