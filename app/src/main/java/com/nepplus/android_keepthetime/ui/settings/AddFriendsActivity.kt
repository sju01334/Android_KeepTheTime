package com.nepplus.android_keepthetime.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.MyFriendsRecyclerAdapter
import com.nepplus.android_keepthetime.databinding.ActivityAddFriendsBinding
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendsActivity : BaseActivity() {

    lateinit var binding : ActivityAddFriendsBinding

    lateinit var mFriendAdapter : MyFriendsRecyclerAdapter

    var mFriendList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friends)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.searchBtn.setOnClickListener {

            val inputNick = binding.searchEdt.text.toString()
            if(inputNick.length < 2){
                Toast.makeText(mContext, "검색어는 2자 이상 작성해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//        서버에서 검색한 친구 목록 가져오기
            apiList.getRequestSearchUser(inputNick).enqueue(object : Callback<BasicResponse>{

                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        val br = response.body()!!

                        mFriendList.clear()
                        mFriendList.addAll(br.data.users)

                        Log.d("받아온 유저 데이터 목록", br.data.users.toString())
                        mFriendAdapter.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    override fun setValues() {
        mFriendAdapter = MyFriendsRecyclerAdapter(mContext, mFriendList)
        binding.findUserRecyclerView.adapter = mFriendAdapter
        binding.findUserRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }
}