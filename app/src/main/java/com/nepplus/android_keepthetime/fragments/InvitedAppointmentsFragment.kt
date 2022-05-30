
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.FragmentInvitedAppointmentsBinding
import com.nepplus.android_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.nepplus.android_keepthetime.fragments.BaseFragment

class InvitedAppointmentsFragment : BaseFragment() {

    lateinit var binding : FragmentInvitedAppointmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invited_appointments, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {
    }
}