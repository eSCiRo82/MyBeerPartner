package com.citibox.mybeerpartner.data.repository

import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import com.citibox.mybeerpartner.model.LocationModel

/**
 * Interface to be implemented by those respositories able to provide Rick and Morty's Universe
 * information
 */
interface DataRepository {

    /** Request characters from the online API */
    fun requestCharacters(page: Int) : RickAndMortyDataRepository.RequestResult

    /** Request episodes from the online API */
    fun requestEpisodes(page: Int): RickAndMortyDataRepository.RequestResult

    /** Request locations from the online API */
    fun requestLocations(page: Int): RickAndMortyDataRepository.RequestResult

    /** Returns the list of characters */
    fun getCharacters(): List<CharacterModel>

    /** Returns the list of locations */
    fun getLocations(page: Int): List<LocationModel>

    /** Returns the list of episodes */
    fun getEpisodes(page: Int): List<EpisodeModel>

    /** Return the location of a character */
    fun getCharacterLocation(character: CharacterModel): LocationModel?

    /** Return the episodes of a character */
    fun getCharacterEpisodes(character: CharacterModel): List<EpisodeModel>

    /** Return the list locations by character */
    fun getCharactersLocations(): Map<CharacterModel, LocationModel?>

    /** Return the list of episodes by character */
    fun getCharactersEpisodes(): Map<CharacterModel, List<EpisodeModel>>

    /** Return the characters located at the same place */
    fun getCharactersAtLocation(location: LocationModel): List<CharacterModel>

    /** Filter the characters using a substring of their name */
    fun filterCharactersByName(name: String): List<CharacterModel>
}