package com.dida.data.interceptor

import android.util.Log
import com.dida.data.DataApplication
import com.dida.data.api.ApiClient.BASE_URL
import com.dida.data.api.MainAPIService
import com.dida.data.shareperference.DataStorePreferences
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.RefreshTokenAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

class BearerInterceptor @Inject constructor(
    private val refeshTokenAPI: RefreshTokenAPI
): Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = ""
        val request = chain.request()
        val response = chain.proceed(request)
        if(response.code == 400){
            runBlocking {
                //토큰 갱신 api 호출
//                val request = DataApplication.mySharedPreferences.getRefreshToken()
                DataApplication.dataStorePreferences.getRefreshToken()?.let {
                    refeshTokenAPI(it)
                        .onSuccess { response ->
//                            DataApplication.mySharedPreferences.setAccessToken(response.accessToken, response.refreshToken)
                            response.accessToken?.let { token ->
                                DataApplication.dataStorePreferences.setAccessToken(token, response.refreshToken)
                                accessToken = token
                            }
                        }
                }
            }
            val newRequest = chain.request().newBuilder().addHeader("Authorization", accessToken)
                .build()
            return chain.proceed(newRequest)
        }
        return response
    }
}