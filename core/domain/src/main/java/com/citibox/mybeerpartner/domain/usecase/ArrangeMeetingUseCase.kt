package com.citibox.mybeerpartner.domain.usecase

import com.citibox.mybeerpartner.common.either.Either
import com.citibox.mybeerpartner.common.usecase.FlowUseCase
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.domain.extension.match
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import com.citibox.mybeerpartner.model.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to arrange a meeting between two characters
 */
class ArrangeMeetingUseCase @Inject constructor(
    private val repository: DataRepository
) : FlowUseCase<ArrangeMeetingUseCase.Input,
        @kotlin.jvm.JvmSuppressWildcards Either<ArrangeMeetingUseCase.Output, UseCaseError>>(Dispatchers.IO) {

    override fun launchFlow(input: Input): Flow<Either<Output, UseCaseError>> = flow {
        repository.getCharacterLocation(input.character)?.let { location ->
            val characterEpisodes = repository.getCharacterEpisodes(input.character)
            val beerPartnerEpisodes = repository.getCharacterEpisodes(input.beerPartner)

            val matchedEpisodes =
                beerPartnerEpisodes.match(characterEpisodes).sortedBy { it.airDate }

            emit(Either.Success(Output(location, matchedEpisodes)))
        } ?: emit(Either.Failure(UseCaseError.NoDataError))
    }

    data class Input(val character: CharacterModel, val beerPartner: CharacterModel)
    data class Output(val location: LocationModel, val episodes : List<EpisodeModel>)
}