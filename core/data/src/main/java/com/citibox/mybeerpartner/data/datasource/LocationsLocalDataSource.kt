package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.database.dao.LocationDao
import com.citibox.mybeerpartner.database.entity.CharactersAtLocationEntity
import com.citibox.mybeerpartner.database.entity.LocationEntity
import javax.inject.Inject

interface LocationsDataSource: MutableDataSource<@JvmSuppressWildcards LocationEntity> {
    fun getCharactersAtLocation(locationId: Long): CharactersAtLocationEntity
}

class LocationsLocalDataSource @Inject constructor(
    private val locationDao: LocationDao
): LocationsDataSource {
    override fun get(): List<LocationEntity> = locationDao.get()

    override fun setAll(element: List<LocationEntity>) = locationDao.insert(element)

    override fun getCharactersAtLocation(locationId: Long): CharactersAtLocationEntity =
        locationDao.getCharactersAtLocation(locationId)

    override fun set(element: LocationEntity) = locationDao.insert(element)

    override fun count(): Int = locationDao.count()
}