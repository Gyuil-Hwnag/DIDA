package com.dida.domain.model.nav.post

data class PostComments(
    val commentId: Int,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
