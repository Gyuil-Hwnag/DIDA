package com.dida.android.presentation.views.nav.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onSettingClicked()
    fun onNftItemClicked(nftId: Long)
}