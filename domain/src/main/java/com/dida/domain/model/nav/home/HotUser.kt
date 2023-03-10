package com.dida.domain.model.nav.home

data class HotUser(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val cardCnt: Long,
    val cardUrls: List<String>,
    val followed: Boolean
)
