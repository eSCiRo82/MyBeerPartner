package com.citibox.mybeerpartner.data.repository


import com.citibox.mybeerpartner.cloud.extension.urlId
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.data.datasource.*
import com.citibox.mybeerpartner.data.extension.toEntity
import com.citibox.mybeerpartner.data.extension.toModel
import com.citibox.mybeerpartner.database.entity.ResourceInfoEntity
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import com.citibox.mybeerpartner.model.LocationModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RickAndMortyDataRepository @Inject constructor(
    private val resourcesInfoLocalDataSource: ResourcesInfoLocalDataSource,
    private val charactersLocalDataSource: CharactersLocalDataSource,
    private val charactersRemoteDataSource: CharactersRemoteDataSource,
    private val locationsLocalDataSource: LocationsLocalDataSource,
    private val locationsRemoteDataSource: LocationsRemoteDataSource,
    private val episodesLocalDataSource: EpisodesLocalDataSource,
    private val episodesRemoteDataSource: EpisodesRemoteDataSource
): DataRepository {

    private val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)
    /** Cached information for improving the speed to process the data */
    private val cachedCharacters = mutableListOf<CharacterModel>()
    private val cachedLocations = mutableListOf<LocationModel>()
    private val cachedEpisodes = mutableListOf<EpisodeModel>()
    private val charactersLocations = mutableMapOf<CharacterModel, LocationModel?>()
    private val charactersEpisodes = mutableMapOf<CharacterModel, List<EpisodeModel>>()

    /**
     * Make a request to the API using the corresponding remote data source
     */
    private fun <R, L> request(page: Int,
                            dataSource: PagedDataSource<ApiResponse<R>?>,
                            localDataSource: DataSource<L>,
                            process: ((ApiResponse<R>) -> Unit)) : RequestResult {
        dataSource.get(page)?.let { response ->
            val count = localDataSource.count()
            if (count >= response.info.count)
                return RequestResult.Finished
            process(response)
            return if (page < response.info.pages) RequestResult.Available else RequestResult.Finished
        } ?: return RequestResult.Failure
    }

    override fun requestCharacters(page: Int): RequestResult =
        request(page, charactersRemoteDataSource, charactersLocalDataSource) { response ->
            resourcesInfoLocalDataSource.set(ResourceInfoEntity("characters", response.info.count, response.info.pages))
            val characters = response.results.map { character ->
                charactersLocalDataSource.setEpisodes(
                    character.id,
                    character.episode.map { url -> url.urlId() })
                character.toEntity().apply {
                    charactersLocalDataSource.setLocation(characterId, location)
                }
            }
            charactersLocalDataSource.setAll(characters)
        }

    override fun requestEpisodes(page: Int): RequestResult =
        request(page, episodesRemoteDataSource, episodesLocalDataSource) { response ->
            resourcesInfoLocalDataSource.set(ResourceInfoEntity("episodes", response.info.count, response.info.pages))
            val entities = response.results.map { episode -> episode.toEntity(dateFormat) }
            episodesLocalDataSource.setAll(entities)
        }

    override fun requestLocations(page: Int): RequestResult =
        request(page, locationsRemoteDataSource, locationsLocalDataSource) { response ->
            resourcesInfoLocalDataSource.set(ResourceInfoEntity("locations", response.info.count, response.info.pages))
            val entities = response.results.map { location -> location.toEntity() }
            locationsLocalDataSource.setAll(entities)
        }

    override fun getCharacters(): List<CharacterModel> {
        resourcesInfoLocalDataSource.get().find { it.resource == "characters" }?.let { resource ->
            if (resource.count > cachedCharacters.size) {
                cachedCharacters.clear()
                cachedCharacters.addAll(charactersLocalDataSource.get().toModel())
            }
        } ?: run { cachedCharacters.addAll(charactersLocalDataSource.get().toModel()) }
        return cachedCharacters
    }

    override fun getLocations(page: Int): List<LocationModel> {
        resourcesInfoLocalDataSource.get().find { it.resource == "locations" }?.let { resource ->
            if (resource.count > cachedLocations.size) {
                cachedLocations.clear()
                cachedLocations.addAll(locationsLocalDataSource.get().toModel())
            }
        } ?: run { cachedLocations.addAll(locationsLocalDataSource.get().toModel()) }
        return cachedLocations
    }

    override fun getEpisodes(page: Int): List<EpisodeModel> {
        resourcesInfoLocalDataSource.get().find { it.resource == "episodes" }?.let { resource ->
            if (resource.count > cachedEpisodes.size) {
                cachedEpisodes.clear()
                cachedEpisodes.addAll(episodesLocalDataSource.get().toModel())
            }
        } ?: run { cachedEpisodes.addAll(episodesLocalDataSource.get().toModel()) }
        return cachedEpisodes
    }

    override fun getCharacterLocation(character: CharacterModel): LocationModel? =
        charactersLocalDataSource.getLocation(character.id).location?.toModel()

    override fun getCharacterEpisodes(character: CharacterModel): List<EpisodeModel> =
        charactersLocalDataSource.getEpisodes(character.id).episodes.toModel()

    override fun getCharactersLocations(): Map<CharacterModel, LocationModel?> {
        if (charactersLocations.isEmpty()) {
            charactersLocations.putAll(charactersLocalDataSource.getCharactersLocations()
                .associateBy({ it.character.toModel() }, { it.location?.toModel() }))
        }
        return charactersLocations
    }

    override fun getCharactersEpisodes(): Map<CharacterModel, List<EpisodeModel>> {
        if (charactersEpisodes.isEmpty()) {
            charactersEpisodes.putAll(charactersLocalDataSource.getCharactersEpisodes()
                .associateBy({ it.character.toModel() }, { it.episodes.toModel() }))
        }
        return charactersEpisodes
    }

    override fun getCharactersAtLocation(location: LocationModel): List<CharacterModel> =
        locationsLocalDataSource.getCharactersAtLocation(location.id).characters.toModel()

    override fun filterCharactersByName(name: String): List<CharacterModel> =
        charactersLocalDataSource.filterByName(name).toModel()

    sealed class RequestResult {
        object Available: RequestResult()
        object Finished: RequestResult()
        object Failure: RequestResult()
    }
}