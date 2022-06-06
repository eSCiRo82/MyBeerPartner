package com.citibox.mybeerpartner.common.either

sealed class Either<out T, out F>
{
    data class Success<out T>(val success: T): Either<T, Nothing>()
    data class Failure<out F>(val failure: F): Either<Nothing, F>()
}