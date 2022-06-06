package com.citibox.mybeerpartner.common.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FlowObserver<T>(owner: LifecycleOwner, private val flow: Flow<T>, private val collector: (suspend (T) -> Unit)? = null) {

    private var job: Job? = null

    init {
        owner.lifecycle.addObserver(
            LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->

                when (event) {
                    Lifecycle.Event.ON_START ->
                        job = source.lifecycleScope.launch {
                            flow.onEach {
                                collector?.invoke(it)
                            }.launchIn(this)
                        }
                    Lifecycle.Event.ON_DESTROY -> {
                        job?.cancel()
                        job = null
                    }
                    else -> {}
                }
            }
        )
    }
}

inline fun <reified T> Flow<T>.flowObserverCollector(lifecycleOwner: LifecycleOwner, noinline collector: suspend (T) -> Unit) =
    FlowObserver(lifecycleOwner, this, collector)