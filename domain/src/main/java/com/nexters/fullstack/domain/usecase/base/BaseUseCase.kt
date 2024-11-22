package com.nexters.fullstack.domain.usecase.base

interface BaseUseCase<Params, Result> {
    fun buildUseCase(params: Params): Result
}