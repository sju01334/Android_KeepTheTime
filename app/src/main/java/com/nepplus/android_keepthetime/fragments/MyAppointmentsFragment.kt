package com.nepplus.android_keepthetime.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.nepplus.android_keepthetime.ui.appointment.EditAppointmentActivity

class MyAppointmentsFragment : BaseFragment() {

    lateinit var binding : FragmentMyAppointmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointments, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.addAppointmentBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
    }
}