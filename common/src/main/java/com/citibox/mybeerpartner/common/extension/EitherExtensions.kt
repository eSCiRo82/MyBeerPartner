package com.citibox.mybeerpartner.common.extension

import com.citibox.mybeerpartner.common.either.Either

inline fun <T, F> Either<T, F>.onSuccess(block: ((T) -> Unit)): Either<T, F> {
    if (this is Either.Success)
        block(this.success)
    return this
}

inline fun <T, F> Either<T, F>.onFailure(block: ((F) -> Unit)): Either<T, F> {
    if (this is Either.Failure)
        block(this.failure)
    return this
}