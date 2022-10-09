package com.dida.android.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    private val _errorEvent: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorEvent: SharedFlow<Throwable> = _errorEvent.asSharedFlow()

    private val _loadingEvent: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loadingEvent: SharedFlow<Boolean> = _loadingEvent.asSharedFlow()

    private val errorHandler = CoroutineExceptionHandler { CoroutineContext, throwable ->
        viewModelScope.launch(CoroutineContext) {
            _errorEvent.emit(throwable)
        }
    }

    fun catchError(e: Throwable?) {
        viewModelScope.launch(errorHandler) {
            e?.let { _errorEvent.emit(it) }
            dismissLoading()
        }
    }

    fun showLoading() {
        baseViewModelScope.launch {
            _loadingEvent.emit(false)
        }
    }

    fun dismissLoading() {
        baseViewModelScope.launch {
            _loadingEvent.emit(true)
        }
    }

    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler
}