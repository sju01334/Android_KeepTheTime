package com.nepplus.android_keepthetime.ui.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.ActivityEditAppointmentBinding
import com.nepplus.android_keepthetime.databinding.ActivityEditMyPlaceBinding

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityEditAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}