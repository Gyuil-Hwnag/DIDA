package com.dida.common.actionhandler

interface CommentActionHandler {
    fun onCommentMoreClicked(commentId: Long)
    fun onCommentUserProfileClicked(userId: Long)
}
