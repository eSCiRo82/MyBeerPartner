package com.citibox.mybeerpartner.data.datasource

interface DataSource<T> {
    fun count(): Int

    fun get(): List<T>
}

interface PagedDataSource<T> {
    fun get(): T

    fun get(page: Int): T
}

interface MutableDataSource<T>: DataSource<T> {
    fun set(element: T)

    fun setAll(element: List<T>)
}