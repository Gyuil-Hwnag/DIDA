package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.mypage.BuySellList
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class BuyListAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<List<BuySellList>> {
        return repository.getBuyList()
    }
}
