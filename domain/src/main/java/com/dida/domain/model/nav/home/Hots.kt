package com.dida.domain.model.nav.home

data class Hots(
    val cardId: Int,
    val nftImg: String,
    val nftName: String,
    val heartCount: String,
    val price: String
){
    fun heartFormat(): String{
        return heartCount
    }
}
