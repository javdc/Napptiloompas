package com.javdc.napptiloompas.domain.util

inline fun <T, R> AsyncResult<T>.map(transform: (T) -> R): AsyncResult<R> {
    return when (this) {
        is AsyncResult.Loading -> AsyncResult.Loading()
        is AsyncResult.Success -> AsyncResult.Success(transform(this.data))
        is AsyncResult.Error -> AsyncResult.Error(this.error)
    }
}