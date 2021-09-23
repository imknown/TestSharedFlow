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

class SecondaryActivity : AppCompatActivity() {
    private val viewModel by viewModels<SecondaryViewModel>()

    private val tvResult by lazy {
        TextView(this).apply {
            textSize = 100F
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(tvResult)

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

    private suspend fun Flow<String>.collectAndSetResult() = collect {
        tvResult.text = it
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