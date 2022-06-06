package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

abstract class DownloadResourcesUseCase :
    FlowUseCase<Unit, Boolean>(Dispatchers.IO) {

    tailrec fun go(page: Int,
                   result: RickAndMortyDataRepository.RequestResult,
                   block: (Int) -> RickAndMortyDataRepository.RequestResult)
        : RickAndMortyDataRepository.RequestResult =
        when (result) {
            RickAndMortyDataRepository.RequestResult.Available -> go(page + 1, block(page), block)
            else -> result
        }
}