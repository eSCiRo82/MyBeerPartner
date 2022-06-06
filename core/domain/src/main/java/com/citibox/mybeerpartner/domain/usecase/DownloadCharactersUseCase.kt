package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to download the characters from the API
 */
class DownloadCharactersUseCase @Inject constructor(
    private val repository: DataRepository
): DownloadResourcesUseCase() {

    override fun launchFlow(input: Unit): Flow<Boolean> = flow {
        emit(go(1, RickAndMortyDataRepository.RequestResult.Available) { page ->
            repository.requestCharacters(page)
        } == RickAndMortyDataRepository.RequestResult.Finished)
    }
}