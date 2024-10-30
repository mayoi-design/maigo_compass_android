package jp.ac.mayoi.core.util

import androidx.compose.runtime.Immutable

@Immutable
sealed class LoadState<out T> {
    abstract val value: T?

    @Immutable
    data class Loading<T>(
        override val value: T?
    ) : LoadState<T>()

    @Immutable
    data class Success<T>(
        override val value: T,
    ) : LoadState<T>()

    @Immutable
    data class Error<T>(
        override val value: T?,
        val error: Throwable,
    ) : LoadState<T>()
}