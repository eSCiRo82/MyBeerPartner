package com.citibox.mybeerpartner.domain.type

sealed class UseCaseError {
    object NoDataError : UseCaseError()
    object NoMatchesError : UseCaseError()
    object WhereAreYouError : UseCaseError()
}
