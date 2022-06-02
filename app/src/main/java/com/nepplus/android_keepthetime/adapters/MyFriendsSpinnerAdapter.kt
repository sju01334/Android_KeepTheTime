package com.nepplus.android_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.models.UserData

class MyFriendsSpinnerAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {

    //    눈에 보여지는 Spinner의 모습
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(resId, null)
        }
        row!!

        val data = mList[position]

        val profileImg = row.findViewById<ImageView>(R.id.profileImg)
        val nicknameTxt = row.findViewById<TextView>(R.id.nicknameTxt)
        val socialLoginImg = row.findViewById<ImageView>(R.id.socialLoginImg)

        nicknameTxt.text = data.nickname

        when (data.provider) {
            "kakao" -> socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
            "facebook" -> socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
            else -> socialLoginImg.visibility = View.GONE
        }


        Glide.with(mContext).load(data.profileImg).into(profileImg)

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}