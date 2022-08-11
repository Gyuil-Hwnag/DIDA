package com.dida.android.presentation.di

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.dida.android.BuildConfig
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.data.shareperference.MySharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility.getKeyHash
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PresentationApplication :Application(){
    // 코틀린의 전역변수 문법
//    companion object {
//        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
//        lateinit var mySharedPreferences: MySharedPreferences
//        lateinit var editor: SharedPreferences.Editor
//    }

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mySharedPreferences = MySharedPreferences(applicationContext)
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        // kakao hash key 추출
        Log.d("getKeyHash", "" + getKeyHash(this))
    }
}