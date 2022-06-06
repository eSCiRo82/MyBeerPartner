package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to filter the characters by their name
 */
class FilterCharactersByNameUseCase @Inject constructor(
    private val repository: DataRepository
) : FlowUseCase<String, List<@kotlin.jvm.JvmSuppressWildcards CharacterModel>>(Dispatchers.IO) {

    override fun launchFlow(input: String): Flow<List<CharacterModel>> = flow {
        emit(repository.filterCharactersByName(input))
    }
}