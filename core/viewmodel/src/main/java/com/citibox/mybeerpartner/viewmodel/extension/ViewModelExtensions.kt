package com.citibox.mybeerpartner.viewmodel.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import kotlinx.coroutines.flow.*

fun <I, R, U: FlowUseCase<I, R>> ViewModel.launchUseCase(useCase: U,
                                                         input: I,
                                                         onStart: (suspend () -> Unit)? = null,
                                                         onEach: (suspend (R) -> Unit)? = null,
                                                         onCompletion: (suspend (Throwable?) -> Unit)? = null,
                                                         catch: (suspend (Throwable) -> Unit)? = null) =
    useCase
        .prepare(input)
        .onStart { onStart?.invoke() }
        .onEach { response -> onEach?.invoke(response) }
        .onCompletion { response -> onCompletion?.invoke(response) }
        .catch { exception -> catch?.invoke(exception) }
        .launchIn(viewModelScope)