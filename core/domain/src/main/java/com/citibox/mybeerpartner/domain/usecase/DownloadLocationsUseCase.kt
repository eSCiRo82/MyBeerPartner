package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to download the locations from the API
 */
class DownloadLocationsUseCase @Inject constructor(
    private val repository: DataRepository
): FlowUseCase<Unit, Boolean>(Dispatchers.IO) {

    override fun launchFlow(input: Unit): Flow<Boolean> = flow {
        tailrec fun go(page: Int, result: RickAndMortyDataRepository.RequestResult) : RickAndMortyDataRepository.RequestResult =
            when (result) {
                RickAndMortyDataRepository.RequestResult.Available -> go(page + 1, repository.requestLocations(page))
                else -> result
            }
        emit(go(1, RickAndMortyDataRepository.RequestResult.Available)
                == RickAndMortyDataRepository.RequestResult.Finished)
    }
}