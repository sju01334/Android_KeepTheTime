package com.nepplus.android_keepthetime.utils

import android.content.Context

class ContextUtil {

    companion object{
        private val prefName = "KeepTheTIme"
        private val LOGIN_TOKEN = "LOGIN_TOKEN"

        fun setLoginToken (context : Context, token : String){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_TOKEN, token).apply()
        }

        fun getLoginToken (context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TOKEN, "")!!
        }

        fun clear(context: Context){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }


    }
}