package com.mercadolibre.productsearchapp.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/***
 * From https://medium.com/swlh/android-click-debounce-80b3f2e638f3
 * https://github.com/A-Kubista/ClickDebounceExample
 **/
fun <T> debounce(
    time: Long = 0,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(time)
            action(param)
        }
    }
}
