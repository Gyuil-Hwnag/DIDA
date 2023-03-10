package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetDetailNFTResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("contracts") val contracts: String?,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("viewerNickname") val viewerNickname: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("price") val price: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("userId") val userId: Long,
    @SerializedName("marketId") val marketId: Long
)
