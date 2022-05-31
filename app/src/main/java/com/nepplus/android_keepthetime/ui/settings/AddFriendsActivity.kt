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

            Log.d("검색어", inputNick)
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
//                        어댑터의 아이템들을 모두 삭제한 뒤
                        mFriendAdapter.notifyItemRangeRemoved(0, mFriendList.size)

//                        리스트를 전면 삭제하고
                        mFriendList.clear()

//                        새로운 (서버에서 내려준) 리스트로 다시 덮어주고
                        mFriendList.addAll(br.data.users)

//                        어댑터의 아이템이 새로 들어왔음을 통보
                        mFriendAdapter.notifyItemRangeInserted(0, mFriendList.size)

//                        어댑터에 리스트가 바뀌었다는 사실 통보
//                        RecyclerView의 모든 뷰를 삭제하고 다시 뷰를 생성 비효율적인 코드
//                        mFriendAdapter.notifyDataSetChanged()

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