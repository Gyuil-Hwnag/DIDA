package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface MainUsecase {
    suspend fun checkVersionAPI(): Response<AppVersionResponse>

    suspend fun loginAPI(idToken : String): NetworkResult<LoginResponseModel>

    suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel>

    suspend fun createUserAPI(email: String, nickName: String): NetworkResult<LoginResponseModel>

    suspend fun getUserProfileAPI() : NetworkResult<UserProfileResponseModel>

    suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel>

    suspend fun getUserCardsAPI() : NetworkResult<List<UserCardsResponseModel>>

    suspend fun getSendEmailAPI() : NetworkResult<RandomNumber>

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : NetworkResult<Unit>

    suspend fun getWalletExistsAPI() : NetworkResult<Boolean>

    suspend fun getCheckPasswordAPI(password: String) : NetworkResult<Boolean>

    suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String) : NetworkResult<Unit>

    suspend fun getTempPasswordAPI() : NetworkResult<Unit>

    suspend fun getMainAPI() : NetworkResult<Flow<Home>>

    suspend fun getSoldOutAPI(term: Int) : NetworkResult<Flow<List<SoldOut>>>
}