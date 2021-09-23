package net.imknown.android.testsharedflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SecondaryViewModel : ViewModel() {
    private val _tryEmitEvent by lazy {
        MutableSharedFlow<String>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }
    val tryEmitEvent by lazy { _tryEmitEvent.asSharedFlow() }

    fun triggerTryEmit() = _tryEmitEvent.tryEmit((1..100).random().toString())
}