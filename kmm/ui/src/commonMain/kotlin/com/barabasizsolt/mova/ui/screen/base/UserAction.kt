package com.barabasizsolt.mova.ui.screen.base

sealed class UserAction {
    object SwipeRefresh : UserAction()
    object Search : UserAction()
    object Error : UserAction()
    object Normal : UserAction()
    object TryAgain : UserAction()
}