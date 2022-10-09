package com.dida.android.presentation.views.login

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.LoginAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val loginAPI: LoginAPI
) : BaseViewModel(), LoginMainActionHandler {

    private val TAG = "LoginMainViewModel"

    private val _navigationEvent: MutableSharedFlow<LoginNavigationAction> = MutableSharedFlow<LoginNavigationAction>()
    val navigationEvent: SharedFlow<LoginNavigationAction> = _navigationEvent

    /** 카카오 로그인 결과
     * refreshToken ->
     * null : 닉네임 입력(처음 회원가입한 유저로 닉네임 입력)
     * notNull : 홈화면 이동(이미 추가회원가입 완료 유저)
     *
     * accessToken ->
     * 추가 회원가입 진행시(email) : email 정보
     * 추가 회원가입 이미 진행 : Token
     **/

    fun loginAPIServer(idToken: String) {
        baseViewModelScope.launch {
            loginAPI(idToken)
                .onSuccess {
                    if(it.refreshToken.isNullOrEmpty()) {
                        _navigationEvent.emit(LoginNavigationAction.NavigateToNickname(it.accessToken!!))
                    }
                    else {
                        dataStorePreferences.setAccessToken(it.accessToken, it.refreshToken)
//                        mySharedPreferences.setAccessToken(it.accessToken, it.refreshToken)
                        _navigationEvent.emit(LoginNavigationAction.NavigateToHome)
                    } }
                .onError { e ->
                    catchError(e)
                    _navigationEvent.emit(LoginNavigationAction.NavigateToLoginFail)
//                    mySharedPreferences.removeAccessToken()
                    dataStorePreferences.removeAccessToken()
                }
        }
    }

    override fun onKakaoLoginClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(LoginNavigationAction.NavigateToLogin)
        }
    }
}