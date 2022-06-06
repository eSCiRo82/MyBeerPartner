package com.citibox.mybeerpartner.viewmodel

import androidx.lifecycle.ViewModel
import com.citibox.mybeerpartner.common.extension.onFailure
import com.citibox.mybeerpartner.common.extension.onSuccess
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.domain.usecase.*
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.LocationModel
import com.citibox.mybeerpartner.viewmodel.extension.launchUseCase
import kotlinx.coroutines.flow.*
import java.sql.Date
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val arrangeMeetingUseCase: ArrangeMeetingUseCase,
    private val downloadCharactersUseCase: DownloadCharactersUseCase,
    private val downloadEpisodesUseCase: DownloadEpisodesUseCase,
    private val downloadLocationsUseCase: DownloadLocationsUseCase,
    private val filterCharactersByNameUseCase: FilterCharactersByNameUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchBeerPartnerUseCase: SearchBeerPartnerUseCase
): ViewModel() {

    private val _characterStates = MutableStateFlow<State>(State.Idle)
    val characterStates: Flow<State> = _characterStates.asStateFlow()

    private val _characterEvents = MutableSharedFlow<Event>()
    val characterEvents: Flow<Event> = _characterEvents.asSharedFlow()

    private var _lastFilter: String = ""
    val lastFilter: String
        get() = _lastFilter

    fun downloadCharacters() = launchUseCase(
        useCase = downloadCharactersUseCase,
        input = Unit,
        onEach = {
            if (it) _characterEvents.emit(Event.DownloadFinished)
            else _characterEvents.emit(Event.DownloadError)
        },
        onCompletion = {
            downloadLocations()
            downloadEpisodes()
        }
    )

    private fun downloadLocations() = launchUseCase(
        useCase = downloadLocationsUseCase,
        input = Unit
    )

    private fun downloadEpisodes() = launchUseCase(
        useCase = downloadEpisodesUseCase,
        input = Unit
    )

    fun getCharacters() = launchUseCase(
        useCase = getCharactersUseCase,
        input = Unit,
        onStart = { _characterStates.value = State.Idle },
        onEach = { response ->
            response.onSuccess { list -> _characterStates.value = State.Characters(list) }
                .onFailure { _characterEvents.emit(Event.NoCharacters) }
        }
    )

    fun filterCharactersByName(name: String) = launchUseCase(
        useCase = filterCharactersByNameUseCase,
        input = name,
        onStart = { _lastFilter = name },
        onEach = { list -> _characterStates.value = State.Characters(list) }
    )

    fun searchBeerPartner(character: CharacterModel) = launchUseCase(
        useCase = searchBeerPartnerUseCase,
        input = character,
        onEach = { result ->
            result.onSuccess { beerPartner ->
                _characterEvents.emit(Event.BeerPartner(character, beerPartner))
            }.onFailure {
                when (it) {
                    UseCaseError.NoDataError -> _characterEvents.emit(Event.NoBeerPartnerFound)
                    UseCaseError.NoMatchesError -> _characterEvents.emit(Event.NoBeerPartnerFound)
                    UseCaseError.WhereAreYouError -> _characterEvents.emit(Event.WhereAreYou)
                }
            }
        }
    )

    fun makeAppointment(character: CharacterModel, beerPartner: CharacterModel) = launchUseCase(
        useCase = arrangeMeetingUseCase,
        input = ArrangeMeetingUseCase.Input(character, beerPartner),
        onEach = { response ->
            response.onSuccess { meeting ->
                val event = Event.Meeting(
                    character, beerPartner,
                    meeting.location,
                    meeting.episodes.size,
                    meeting.episodes.first().airDate,
                    meeting.episodes.last().airDate
                )
                _characterEvents.emit(
                    event
                )
            }
        }
    )

    sealed class State {
        object Idle : State()
        data class Characters(val characters: List<CharacterModel>) : State()
    }

    sealed class Event {
        object DownloadFinished : Event()
        object DownloadError : Event()
        object NoCharacters : Event()
        data class BeerPartner(val character: CharacterModel,
                               val beerPartner: CharacterModel) : Event()
        data class Meeting(val character: CharacterModel,
                           val beerPartner: CharacterModel,
                           val location: LocationModel,
                           val episodes : Int,
                           val meetingEpisode: Date,
                           val lastEpisode: Date) : Event()
        object WhereAreYou : Event()
        object NoBeerPartnerFound: Event()
    }
}
