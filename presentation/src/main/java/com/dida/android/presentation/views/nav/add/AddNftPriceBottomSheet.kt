package com.dida.android.presentation.views.nav.add

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.android.databinding.BottomAddNftPriceBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNftPriceBottomSheet(val price: (String) -> Unit) :
    BaseBottomSheetDialogFragment<BottomAddNftPriceBinding, AddNftPriceViewModel>() {

    private val TAG = "AddNftPriceBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_add_nft_price

    override val viewModel: AddNftPriceViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
        viewModel.okBtnState(false)
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.priceTxt.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            var job: Job? = null

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                job?.cancel()
                searchFor = searchText
                job = lifecycleScope.launch {
                    delay(500)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    if(s.isEmpty() || binding.priceTxt.text.toString().isEmpty()){
                        viewModel.okBtnState(false)
                    }
                    else{
                        viewModel.okBtnState(true)
                    }
                }
            }
        })

        binding.okBtn.setOnClickListener {
            if(viewModel.okBtnStateLiveData.value == true) {
                // NFT 생성 API호출
                price(binding.priceTxt.text.toString())
                dismiss()
            }
        }
    }
}