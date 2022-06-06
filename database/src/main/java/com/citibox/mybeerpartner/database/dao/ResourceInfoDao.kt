package com.citibox.mybeerpartner.database.dao

import androidx.room.*
import com.citibox.mybeerpartner.database.entity.*

@Dao
interface ResourceInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(info: ResourceInfoEntity)

    @Update
    fun update(info: ResourceInfoEntity): Int

    @Query("SELECT * FROM resources_info")
    fun get(): List<ResourceInfoEntity>

    @Query("SELECT * FROM resources_info WHERE resource=:id")
    fun get(id: String): ResourceInfoEntity?

    @Query("SELECT COUNT(*) FROM resources_info")
    fun count(): Int

    @Query("DELETE FROM resources_info")
    fun clear()
}