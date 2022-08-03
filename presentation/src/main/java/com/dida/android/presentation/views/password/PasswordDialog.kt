package com.dida.android.presentation.views.password

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.DialogPasswordBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDialog :
    BaseBottomSheetDialogFragment<DialogPasswordBinding, PasswordViewModel>() {

    private val TAG = "PasswordDialog"

    override val layoutResourceId: Int
        get() = R.layout.dialog_password

    override val viewModel: PasswordViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
        dialogFullScreen()
    }

    override fun initDataBinding() {
        viewModel.completeLiveData.observe(viewLifecycleOwner){
            val dialog = PasswordReconfirmDialog()
            dialog.show(childFragmentManager, "PasswordDialog")
            dialog.setPasswordCallbackListener(object :
                PasswordReconfirmDialog.PasswordCallbackListener {
                override fun callbackPassword(passwordReconfirm: String) {
                    if(viewModel.stackToString().equals(passwordReconfirm)){
                        Toast.makeText(requireContext(), "패스워드 같음", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "패스워드 다름", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.clearStack()
                }
            })
        }
    }

    override fun initAfterBinding() {
        binding.dialogPaymentKeypad0.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad1.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad2.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad3.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad4.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad5.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad6.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad7.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad8.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad9.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypadBack.setOnClickListener {
            buttonVibrator()
            viewModel.removeStack()
        }
    }

    private fun dialogFullScreen() {
        if (dialog != null) {
            val bottomSheet: View =
                dialog!!.findViewById(androidx.navigation.ui.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val view = view
        view!!.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private val numberClickLister: View.OnClickListener = View.OnClickListener {
        buttonVibrator()
        viewModel.addStack((it as TextView).text.toString().toInt())
    }

    private fun buttonVibrator() {
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, 50));
        } else {
            vibrator.vibrate(1000);
        }
    }
}