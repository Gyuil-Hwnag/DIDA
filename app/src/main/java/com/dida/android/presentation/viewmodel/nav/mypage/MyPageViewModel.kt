package com.dida.android.presentation.viewmodel.nav.mypage

import com.dida.android.data.repository.MainRepository
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "MyPageViewModel"


}