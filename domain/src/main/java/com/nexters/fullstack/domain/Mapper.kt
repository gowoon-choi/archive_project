package com.nexters.fullstack.domain

interface Mapper<D, T> {
    fun toData(data: D): T

    fun fromData(data: T): D
}