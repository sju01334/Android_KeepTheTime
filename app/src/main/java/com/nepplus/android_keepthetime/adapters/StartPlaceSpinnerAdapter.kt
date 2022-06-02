package com.nepplus.android_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.models.PlaceData

class StartPlaceSpinnerAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<PlaceData>
) : ArrayAdapter<PlaceData>(mContext, resId, mList) {

    //    눈에 보여지는 Spinner 의 모습
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(resId, null)
        }
        row!!

        val data = mList[position]
        val placeNameTxt = row.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = row.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPalceMapImg = row.findViewById<ImageView>(R.id.viewPalceMapImg)

        placeNameTxt.text = data.name
        if(!data.isPrimary){
            isPrimaryTxt.visibility = View.GONE
        }
        viewPalceMapImg.visibility = View.GONE

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(resId, null)
        }
        row!!

        val data = mList[position]
        val placeNameTxt = row.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = row.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPalceMapImg = row.findViewById<ImageView>(R.id.viewPalceMapImg)

        placeNameTxt.text = data.name
        if(!data.isPrimary){
            isPrimaryTxt.visibility = View.GONE
        }
        viewPalceMapImg.visibility = View.GONE

        return row
    }
}