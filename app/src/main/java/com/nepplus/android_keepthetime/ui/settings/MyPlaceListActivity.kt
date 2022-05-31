package com.nepplus.android_keepthetime.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.PlaceRecyclerAdapter
import com.nepplus.android_keepthetime.databinding.ActivityMyPlaceListBinding
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.models.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlaceListActivity : BaseActivity() {

    lateinit var binding : ActivityMyPlaceListBinding

    lateinit var mPlaceRecyclerAdapter : PlaceRecyclerAdapter
    var mPlaceList = ArrayList<PlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_place_list)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getMyPlaceListFromServer()
    }

    override fun setupEvents() {


    }

    override fun setValues() {

        titleTxt.text = "'"
        addBtn.visibility = View.VISIBLE

        mPlaceRecyclerAdapter = PlaceRecyclerAdapter(mContext, mPlaceList)
        binding.myPlaceRecyclerView.adapter = mPlaceRecyclerAdapter
        binding.myPlaceRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getMyPlaceListFromServer(){
        apiList.getRequestUserMyPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!

                    mPlaceList.clear()
                    mPlaceList.addAll(br.data.places)

                    mPlaceRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}