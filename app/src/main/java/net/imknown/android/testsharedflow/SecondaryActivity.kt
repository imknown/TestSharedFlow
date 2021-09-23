package net.imknown.android.testsharedflow

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SecondaryActivity : AppCompatActivity(android.R.layout.simple_list_item_1) {
    private val viewModel by viewModels<SecondaryViewModel>()

    private val text by lazy { findViewById<TextView>(android.R.id.text1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        approach1()
//        approach2()
//        approach3()

        if (savedInstanceState == null) {
            viewModel.triggerTryEmit()
        }
    }

    private fun launchAndTryEmit(
        action: suspend (SharedFlow<String>) -> Unit
    ) = lifecycleScope.launch {
        action(viewModel.tryEmitEvent)
    }

    private suspend fun Flow<String>.collectAndSetResult() {
        collect { text.text = it }
    }

    private fun approach1() = launchAndTryEmit {
        it.collectAndSetResult()
    }

    private fun approach2() = launchAndTryEmit {
        it.flowWithLifecycle(lifecycle).collectAndSetResult()
    }

    private fun approach3() = launchAndTryEmit {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            it.collectAndSetResult()
        }
    }
}