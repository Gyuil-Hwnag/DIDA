package com.dida.android.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.GlobalApplication
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.model.login.NicknameResponseModel
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "NicknameViewModel"

    private val _nickNameSuccessLiveData = MutableLiveData<Boolean>()
    val nickNameSuccessLiveData: LiveData<Boolean>
        get() = _nickNameSuccessLiveData

    suspend fun nicknameAPIServer(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.nicknamePIServer(nickName).let {
                if(it.isSuccessful){
                    _nickNameSuccessLiveData.postValue(it.body()!!.used)
                }
                else{
                    _nickNameSuccessLiveData.postValue(true)
                }
            }
        }
    }
}