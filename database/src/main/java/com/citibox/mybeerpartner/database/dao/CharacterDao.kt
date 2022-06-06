package com.citibox.mybeerpartner.database.dao

import androidx.room.*
import com.citibox.mybeerpartner.database.entity.*

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(characterEpisodes: List<CharacterEpisodesRefs>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterLocation: CharacterLocationRefs)

    @Update
    fun update(character: CharacterEntity): Int

    @Query("SELECT * FROM characters")
    fun get(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE character_id=:id")
    fun get(id: Long): CharacterEntity?

    @Query("SELECT * FROM characters WHERE name=:name")
    fun get(name: String): CharacterEntity?

    @Query("SELECT * FROM characters WHERE name like '%' || :filter || '%'")
    fun filterByName(filter: String): List<CharacterEntity>

    @Transaction
    @Query("SELECT * FROM characters")
    fun getCharactersAndEpisodes(): List<CharacterAndEpisodesEntity>

    @Transaction
    @Query("SELECT * FROM characters WHERE character_id=:id")
    fun getEpisodes(id: Long): CharacterAndEpisodesEntity

    @Transaction
    @Query("SELECT * FROM characters")
    fun getCharactersAndLocations(): List<CharacterAndLocationEntity>

    @Transaction
    @Query("SELECT * FROM characters WHERE character_id=:id")
    fun getLocation(id: Long): CharacterAndLocationEntity

    @Query("SELECT COUNT(*) FROM characters")
    fun count(): Int
}