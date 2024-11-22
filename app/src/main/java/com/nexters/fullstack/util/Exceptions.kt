package com.nexters.fullstack.util

sealed class Exceptions : Throwable(){
    abstract val errorMessage : String
}

object NotFoundViewType : Exceptions() {
    override val errorMessage: String = "존재하지 않는 ViewType 입니다."
}

object NotFoundViewState : Exceptions(){
    override val errorMessage: String = "존재하지 않는 ViewState 입니다."
}