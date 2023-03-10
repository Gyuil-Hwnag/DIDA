package com.dida.community_detail

import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.domain.flatMap
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCommunityViewModel @Inject constructor(
    private val postIdAPI: PostIdAPI,
    private val commentsPostIdAPI: CommentsPostIdAPI,
    private val commentAPI: CommentAPI,
    private val deleteCommentAPI: DeleteCommentAPI,
    private val deletePostAPI: DeletePostAPI
) : BaseViewModel(), DetailCommunityActionHandler, CommentActionHandler {

    private val TAG = "DetailCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailCommunityNavigationAction> = MutableSharedFlow<DetailCommunityNavigationAction>()
    val navigationEvent: SharedFlow<DetailCommunityNavigationAction> = _navigationEvent.asSharedFlow()

    private val _postState: MutableStateFlow<Post?> = MutableStateFlow(null)
    val postState: StateFlow<Post?> = _postState.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comments>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comments>> = _commentList.asStateFlow()

    val commentState: MutableStateFlow<String> = MutableStateFlow("")

    val isWrite: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    fun getPost(postId: Long) {
        baseViewModelScope.launch {
            showLoading()
            postIdAPI.invoke(postId = postId)
                .onSuccess { _postState.value = it }
                .flatMap { commentsPostIdAPI.invoke(postId = postId) }
                .onSuccess {
                    _commentList.value = it
                    isWrite.value = false }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun updateCommunity() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUpdateCommunity(postId = postState.value!!.postId))
        }
    }

    fun deleteComment(commentId: Long) {
        baseViewModelScope.launch {
            deleteCommentAPI(commentId = commentId)
                .onSuccess { getPost(postId = postState.value!!.postId) }
                .onError { e -> catchError(e) }
        }
    }

    fun deleteCommunity() {
        baseViewModelScope.launch {
            showLoading()
            deletePostAPI.invoke(postState.value!!.postId)
                .onSuccess { _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToBack) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCommentClicked() {
        baseViewModelScope.launch {
            if(commentState.value.isNotBlank()) {
                commentAPI(postId = postState.value!!.postId, content = commentState.value)
                    .onSuccess { commentState.value = "" }
                    .flatMap { commentsPostIdAPI.invoke(postId = postState.value!!.postId) }
                    .onSuccess {
                        isWrite.value = true
                        _commentList.value = it }
                    .onError { e -> catchError(e) }
            }
        }
    }

    override fun onCommunityMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCommunityMore)
        }
    }

    override fun onCommentMoreClicked(commentId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCommentMore(commentId = commentId))
        }
    }

    override fun onCommentUserProfileClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = userId))
        }
    }

    override fun onUserProfileClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = postState.value!!.userId))
        }
    }

    override fun onCardClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCardDetail(cardId = postState.value!!.cardId))
        }
    }
}
