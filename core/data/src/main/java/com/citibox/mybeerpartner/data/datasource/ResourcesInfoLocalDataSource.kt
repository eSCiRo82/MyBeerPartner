package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.database.dao.ResourceInfoDao
import com.citibox.mybeerpartner.database.entity.ResourceInfoEntity
import javax.inject.Inject

class ResourcesInfoLocalDataSource @Inject constructor(
    private val resourceInfoDao: ResourceInfoDao
): MutableDataSource<@JvmSuppressWildcards ResourceInfoEntity> {
    override fun get(): List<ResourceInfoEntity> = resourceInfoDao.get()

    override fun set(element: ResourceInfoEntity) = resourceInfoDao.insert(element)

    override fun setAll(element: List<ResourceInfoEntity>) = Unit

    override fun count(): Int = resourceInfoDao.count()
}