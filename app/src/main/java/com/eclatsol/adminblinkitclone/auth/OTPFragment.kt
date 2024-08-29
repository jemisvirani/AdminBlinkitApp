package com.eclatsol.adminblinkitclone.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eclatsol.adminblinkitclone.activity.AdminMainActivity
import com.eclatsol.adminblinkitclone.R
import com.eclatsol.adminblinkitclone.Utils
import com.eclatsol.adminblinkitclone.databinding.FragmentOtpBinding
import com.eclatsol.adminblinkitclone.models.Admins
import com.eclatsol.adminblinkitclone.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {

    private val viewModel : AuthViewModel by viewModels()
    private lateinit var binding: FragmentOtpBinding
    private lateinit var userNumer: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        getUserNumber()
        customizingEnteringOtp()
        sendOtp()
        onLoginButtonClicked()
        onBackButtonClicked()
        return binding.root
    }

    private fun onLoginButtonClicked() {
        binding.btnLogin.setOnClickListener {
            Utils.showToast(requireContext(), "Signing you...")
            val editTexts = arrayOf(binding.etOtp1,
                binding.etOtp2,
                binding.etOtp3,
                binding.etOtp4,
                binding.etOtp5,
                binding.etOtp6)
            val otp = editTexts.joinToString(""){it.text.toString()}
            if (otp.length < editTexts.size){
                Utils.showToast(requireContext(), "Please enter right otp")
            }else{
                editTexts.forEach {
                    it.text?.clear();it.clearFocus()
                }
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        val user = Admins(uid = null,userPhoneNumber = userNumer)
        viewModel.signInWithPhoneAuthCredential(otp,userNumer,user)
        lifecycleScope.launch {
            viewModel.isSignedInSuccessfully.collect{
                if (it){
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Logged In...")
                    startActivity(Intent(requireActivity(), AdminMainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    private fun sendOtp() {
        Utils.showDialog(requireContext(), "Sending OTP...")
        viewModel.apply{
            sendOtp(userNumer,requireActivity())
            lifecycleScope.launch{
                otpSent.collect{
                    if (it){
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "Otp sent...")
                    }
                }
            }

        }
    }

    private fun customizingEnteringOtp() {
        val editTexts = arrayOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (p0?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }

            })
        }
    }


    private fun getUserNumber() {
        val bundle = arguments
        userNumer = bundle?.getString("number").toString()
        binding.tvUserNumber.text = userNumer
    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_signInFragment)
        }
    }

}