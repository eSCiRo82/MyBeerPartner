package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.either.Either
import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to obtain the list of characters
 */
class GetCharactersUseCase @Inject constructor(
    private val repository: DataRepository
): FlowUseCase<Unit, @JvmSuppressWildcards Either<List<CharacterModel>, UseCaseError>>(Dispatchers.IO)
{
    override fun launchFlow(input: Unit): Flow<Either<List<@JvmSuppressWildcards CharacterModel>, UseCaseError>> = flow {
        val characters = repository.getCharacters()
        emit(   if (characters.isEmpty()) Either.Failure(UseCaseError.NoDataError)
                else Either.Success(characters))
    }
}