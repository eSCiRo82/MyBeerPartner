package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.either.Either
import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.domain.extension.getEpisodesTogether
import com.citibox.mybeerpartner.domain.extension.getFirstEpisodeTogether
import com.citibox.mybeerpartner.domain.extension.getLastEpisodeTogether
import com.citibox.mybeerpartner.domain.extension.groupCharactersByNumberEpisodes
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to search the perfect beer partner for the given character
 */
class SearchBeerPartnerUseCase @Inject constructor(
    private val repository: DataRepository
) : FlowUseCase<CharacterModel, @kotlin.jvm.JvmSuppressWildcards Either<CharacterModel, UseCaseError>>(Dispatchers.IO) {

    private fun getCharactersAtLocation(characterModel: CharacterModel) = repository.getCharactersEpisodes().mapNotNull {
        if (it.key.id != characterModel.id && it.key.location == characterModel.location) it.key to it.value
        else null
    }

    override fun launchFlow(input: CharacterModel): Flow<Either<CharacterModel, UseCaseError>> = flow {
        emit(
            if (input.location == 0L) {
                Either.Failure(UseCaseError.WhereAreYouError)
            } else {
                // Filter characters by location where the selected character is at
                val charactersAtLocation = getCharactersAtLocation(input)
                if (charactersAtLocation.size > 1) {
                    val selectedEpisodes = repository.getCharacterEpisodes(input)
                    // If more than one character is at the location, get the episodes where the
                    // characters coincided with the selected
                    val matchedEpisodes = charactersAtLocation.getEpisodesTogether(selectedEpisodes)
                    // Then, search for the character that coincided more times
                    val numberEpisodes = matchedEpisodes.groupCharactersByNumberEpisodes().entries
                    if (numberEpisodes.size > 0) {
                        val moreEpisodes = numberEpisodes.last().value

                        if (moreEpisodes.size > 1) {
                            // If more than one characters share the same number of episode
                            // search for the character whose first episode is older
                            val firstEpisodeTogether = moreEpisodes.getFirstEpisodeTogether()

                            if (firstEpisodeTogether.value.size > 1) {
                                // If not, search for the character whose episode is older
                                val lastEpisodeTogether = moreEpisodes.getLastEpisodeTogether()

                                if (lastEpisodeTogether.value.size > 1) {
                                    // In the end, select the partner with the minimum ID
                                    lastEpisodeTogether.value.minByOrNull {
                                        it.first.id
                                    }?.let {
                                        Either.Success(it.first)
                                    }
                                        ?: Either.Failure(UseCaseError.NoMatchesError)
                                }
                                else
                                    Either.Success(lastEpisodeTogether.value.first().first)
                            } else if (charactersAtLocation.isEmpty()) {
                                Either.Failure(UseCaseError.NoMatchesError)
                            }
                            else
                               Either.Success(firstEpisodeTogether.value.first().first)
                        } else if (charactersAtLocation.isEmpty()) {
                            Either.Failure(UseCaseError.NoMatchesError)
                        }
                        else
                            Either.Success(moreEpisodes[0].first)
                    } else {
                        Either.Failure(UseCaseError.NoMatchesError)
                    }
                } else if (charactersAtLocation.isEmpty()) {
                    Either.Failure(UseCaseError.NoMatchesError)
                }
                else
                    Either.Success(charactersAtLocation.first().first)
            }
        )
    }
}