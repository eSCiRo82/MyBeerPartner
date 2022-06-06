package com.citibox.mybeerpartner.database.dao

import androidx.room.*
import com.citibox.mybeerpartner.database.entity.CharacterAndLocationEntity
import com.citibox.mybeerpartner.database.entity.CharactersAtLocationEntity
import com.citibox.mybeerpartner.database.entity.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: List<LocationEntity>)

    @Update
    fun update(location: LocationEntity): Int

    @Query("SELECT * FROM locations")
    fun get(): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE location_id=:id")
    fun get(id: Long): LocationEntity?

    @Query("SELECT * FROM locations WHERE name=:name")
    fun get(name: String): LocationEntity?

    @Query("SELECT * FROM locations WHERE location_id=:id")
    fun getCharactersAtLocation(id: Long): CharactersAtLocationEntity

    @Query("SELECT COUNT(*) FROM locations")
    fun count(): Int

    @Query("DELETE FROM locations")
    fun clear()
}