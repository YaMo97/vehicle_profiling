package com.turbocare.vehicleprofiling.ui.profile.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.turbocare.vehicleprofiling.R
import com.turbocare.vehicleprofiling.data.model.VehicleProfile
import com.turbocare.vehicleprofiling.databinding.VehicleMakeFragmentBinding
import com.turbocare.vehicleprofiling.databinding.VehicleProfileFragmentBinding
import com.turbocare.vehicleprofiling.ui.profile.list.VehicleListViewModel
import com.turbocare.vehicleprofiling.util.getViewModel

class VehicleProfileFragment : Fragment() {

    companion object {

        const val PARAM_REGISTRATION_NUMBER = "registration-number"

        fun newInstance(registrationNumber: String) = VehicleProfileFragment().apply {
            arguments = Bundle().apply {
                putString(PARAM_REGISTRATION_NUMBER, registrationNumber)
            }
        }
    }

    private var binding: VehicleProfileFragmentBinding? = null

    private lateinit var viewModel: VehicleProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VehicleProfileFragmentBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(VehicleProfileViewModel::class.java)

        setupObservers()

        val registrationNumber = arguments?.getString(PARAM_REGISTRATION_NUMBER)
        registrationNumber?.let {
            viewModel.getVehicleProfile(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun setupObservers() {
        viewModel.getVehicleProfileLiveData().observe(viewLifecycleOwner, { vehicleProfile ->

            vehicleProfile?.run {
                binding?.button?.text = vehicleProfile.displayName
            }

        })
    }
}