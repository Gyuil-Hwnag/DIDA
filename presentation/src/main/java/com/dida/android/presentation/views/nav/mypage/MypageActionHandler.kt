package com.dida.android.presentation.views.nav.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onLogoutClicked()
    fun onUpdateProfileClicked()
    fun onMypageNftTypeClicked(type : MyPageViewModel.MypageNftType)
}