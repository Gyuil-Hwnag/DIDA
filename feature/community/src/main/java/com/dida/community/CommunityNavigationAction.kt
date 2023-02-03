package com.dida.community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val communityId: Int): CommunityNavigationAction()
    object NavigateToCommunityWrite: CommunityNavigationAction()
    class NavigateToNftDetail(val cardId: Long): CommunityNavigationAction()
}
