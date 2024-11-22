package com.nexters.fullstack.data.local.filesystem

import io.reactivex.Completable

interface FileSystemImages {
    fun delete(path: String): Completable

    fun fetch(filterValue: String): ArrayList<String>

    fun getSize(): Int
}