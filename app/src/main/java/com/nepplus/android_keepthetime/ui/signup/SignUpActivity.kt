package com.nepplus.android_keepthetime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.api.APIList
import com.nepplus.android_keepthetime.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    var isEmailDupOk = false
    var isNickDupOk = false
    var isPwDupOk = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {
            //        이메일 중복 체크
            if(!isEmailDupOk){
                Toast.makeText(mContext, "이메일 중복체크 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //        비밀번호 중복 체크
            else if (!isPwDupOk){
                Toast.makeText(mContext, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //        닉네임 중복 체크
            else if(isNickDupOk){
                Toast.makeText(mContext, "비밀번호 중복체크 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                singUp()
            }
        }

        binding.emailDupBtn.setOnClickListener {

        }

        binding.nickDupBtn.setOnClickListener {

        }

    }

    override fun setValues() {

    }
//    실제로 모든 조건 통과시 실행할 회원가입 API
    fun singUp(){


    }

    fun dupCheck(type : String , value : String){


    }
}