package com.javdc.napptiloompas.common.util

import co.touchlab.kermit.Logger
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> tryOrNull(
    logMessage: String = "Caught exception in tryOrNull, will return null.",
    logTag: String = "tryOrNull",
    logException: Boolean = true,
    block: () -> T,
): T? {
    return try {
        block()

    } catch (e: CancellationException) {
        throw e

    } catch (e: Exception) {
        if (logException) Logger.e(logMessage, e, logTag)
        null
    }
}
