package com.citibox.mybeerpartner.common.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<I, O>(protected open val dispatcher: CoroutineDispatcher) {

    fun prepare(input: I) = launchFlow(input).flowOn(dispatcher)

    protected abstract fun launchFlow(input: I): Flow<O>
}