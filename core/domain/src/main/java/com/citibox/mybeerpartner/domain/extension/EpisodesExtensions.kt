package com.citibox.mybeerpartner.domain.extension

import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel

fun List<EpisodeModel>.match(matches: List<EpisodeModel>) = filter { matches.contains(it) }

fun List<Pair<CharacterModel, List<EpisodeModel>>>.getEpisodesTogether(selectedEpisodes: List<EpisodeModel>):
        List<Pair<CharacterModel, List<EpisodeModel>>> {
    val selectedEpisodesIds = selectedEpisodes.map { it.id }
    return mapNotNull { entry ->
        entry.second.filter { selectedEpisodesIds.contains(it.id) }.let {
            if (it.isEmpty()) null
            else entry.first to it
        }
    }
}

fun List<Pair<CharacterModel, List<EpisodeModel>>>.getFirstEpisodeTogether() = mapNotNull { entry ->
    entry.second.minByOrNull { it.airDate }?.let {
        entry.first to it
    }
}.groupBy { it.second.airDate }.toSortedMap().entries.first()

fun List<Pair<CharacterModel, List<EpisodeModel>>>.getLastEpisodeTogether() = mapNotNull { entry ->
    entry.second.maxByOrNull { it.airDate }?.let {
        entry.first to it
    }
}.groupBy { it.second.airDate }.toSortedMap().entries.first()

fun List<Pair<CharacterModel, List<EpisodeModel>>>.groupCharactersByNumberEpisodes() =
    groupBy { it.second.size }.toSortedMap()